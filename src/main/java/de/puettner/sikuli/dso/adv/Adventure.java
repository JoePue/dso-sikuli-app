package de.puettner.sikuli.dso.adv;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import de.puettner.sikuli.dso.DSOService;
import de.puettner.sikuli.dso.LocationMath;
import de.puettner.sikuli.dso.commands.ui.IslandCommands;
import de.puettner.sikuli.dso.commands.ui.MenuBuilder;
import de.puettner.sikuli.dso.commands.ui.StarMenu;
import de.puettner.sikuli.dso.commands.ui.StarMenuFilter;
import lombok.extern.java.Log;
import org.sikuli.script.Location;
import org.sikuli.script.Match;
import org.sikuli.script.Region;

import javax.annotation.Nullable;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import static de.puettner.sikuli.dso.adv.AdventureStepState.*;
import static de.puettner.sikuli.dso.commands.ui.SikuliCommands.pattern;
import static de.puettner.sikuli.dso.commands.ui.StarMenuFilter.GeneralsFilterString;

@Log
public abstract class Adventure {

    private static final ObjectMapper objectMapper = new ObjectMapper();
    protected final Region region;
    protected final List<NavigationPoint> navPoints = new ArrayList<>();
    private final StarMenu starMenu;
    private final DSOService dsoService;
    protected List<AdventureStep> adventureSteps = new ArrayList<>();
    protected IslandCommands islandCmds;
    protected GeneralMenu generalMenu;

    protected Adventure(IslandCommands islandCmds, StarMenu starMenu, DSOService dsoService) {
        this.islandCmds = islandCmds;
        this.starMenu = starMenu;
        this.generalMenu = MenuBuilder.build().buildGeneralMenu();
        this.region = islandCmds.getIslandRegion();
        this.dsoService = dsoService;

        this.fillNavigationPointsList();

        SimpleModule module = new SimpleModule();
        module.addDeserializer(AttackCamp.class, new AttackCampDeserializer());
        objectMapper.registerModule(module);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    protected abstract void fillNavigationPointsList();

    public abstract void route(NavigationPoint startingPoint, NavigationPoint targetPoint, Dimension targetDragDropOffset, Dimension
            targetNavPointClickOffset);

    public abstract void routeCheck();

    public abstract List<NavigationPoint> getNavigationPoints();

    protected abstract File getFilename();

    public void play() {
        log.info("play");
        this.restoreState();
        this.routeCheck();
        this.initZoom();
        islandCmds.typeESC();
        islandCmds.closeChat();
        try {
            AdventureStep step;
            boolean supportedStep;
            for (int i = 0; i < this.adventureSteps.size(); ++i) {
                step = this.adventureSteps.get(i);
                supportedStep = false;
                if (!DONE.equals(step.getState())) {
                    log.info(step.toString());
                }
                // *** OPEN ***
                if (OPEN.equals(step.getState())) {
                    processStepDelay(step);
                    // *** OPEN - MOVE ***
                    if (StepType.ALL_BACK_TO_STAR_MENU.equals(step.getStepType())) {
                        supportedStep = true;
                        processStepDelay(step);
                        if (!putAllGeneralsBackToStarMenu()) {
                            throw new IllegalStateException("Failed to process step: " + step);
                        }
                        saveState(step, DONE);
                    }
                    // *** OPEN - LAND ***
                    if (StepType.LAND.equals(step.getStepType()) && OPEN.equals(step.getState())) {
                        supportedStep = true;
                        if (!landGeneral(step)) {
                            throw new IllegalStateException("Failed to process step: " + step);
                        }
                        saveState(step, DONE);
                    }
                    // *** MOVE ***
                    if (StepType.MOVE.equals(step.getStepType())) {
                        supportedStep = true;
                        Objects.requireNonNull(step.getTargetNavPoint());
                        // Objects.requireNonNull(step.getTargetDragDropOffset());
                        if (OPEN.equals(step.getState())) {
                            processMoveStep(step);
                            saveState(step, AdventureStepState.DONE);
                        }
                    }
                    // *** OPEN - ATTACK ***
                    if (StepType.ATTACK.equals(step.getStepType())) {
                        supportedStep = true;
                        if (this.prepareAttack(step)) {
                            saveState(step, PREPARED);
                            islandCmds.typeESC();
                        } else {
                            throw new IllegalStateException("Attack preparation failed");
                        }
                    }
                    // *** OPEN - SOLVE_QUEST ***
                    if (StepType.SOLVE_QUEST.equals(step.getStepType())) {
                        if (!processSolveQuestStep(step)) {
                            throw new IllegalStateException("step processing failed");
                        }
                        saveState(step, DONE);
                    }
                    // *** OPEN - UNSET_UNITS ***
                    if (StepType.UNSET_UNITS.equals(step.getStepType())) {
                        if (!processUnsetUnitsStep(step)) {
                            throw new IllegalStateException("step processing failed");
                        }
                        saveState(step, DONE);
                    }
                }
                if (PREPARED.equals(step.getState())) {
                    processStepDelay(step);
                    // *** PREPARED - ATTACK ***
                    if (StepType.ATTACK.equals(step.getStepType())) {
                        supportedStep = true;
                        int errorCode = attack(step);
                        if (errorCode == 0) {
                            saveState(step, DONE);
                            markPreviousStepsAsDone(step, i);
                        } else if (errorCode == 5) {
                            // Camp nicht gefunden, so gilt es als schon besiegt.
                            // saveState(step, AdventureStepState.DONE);
                            throw new IllegalStateException("attack step failed");
                        } else {
                            throw new IllegalStateException("attack step failed");
                        }
                    }
                }
                if (DONE.equals(step.getState())) {
                    supportedStep = true;
                }
                if (!supportedStep) {
                    throw new IllegalStateException("Unsupported adventure step: " + step);
                }
            }
            log.info("All adventure steps processed");
        } catch (Exception e) {
            log.log(Level.SEVERE, e.getMessage(), e);
            // e.printStackTrace();
        } finally {
            saveState();
        }
    }

    private void processStepDelay(AdventureStep step) {
        if (step.getDelay() != null && step.getDelay() > 0) {
            log.info("processStepDelay()");
            islandCmds.sleep(step.getDelay(), TimeUnit.SECONDS);
        }
    }

    private boolean landGeneral(AdventureStep step) {
        log.info("landGeneral");
        centerNavigationPoint(getFirstNavigationPoint());
        boolean rv = false;
        if (this.openGeneralMenu(step.getGeneral(), step.getGeneralName())) {
            this.highlightRegion();
            islandCmds.sleep();
            Optional<Match> match = this.findLandingLocation();
            if (match.isPresent()) {
                match.get().hover();
                match.get().doubleClick();
                rv = true;
            } else {
                throw new IllegalStateException("Missing landing location");
            }
        }
        return rv;
    }

    private void highlightRegion() {
        this.region.highlight(3, "green");
    }

    private Optional<Match> findLandingLocation() {
        return Optional.ofNullable(islandCmds.find((pattern("landing-location-1-zoom1.png").similar(0.90)), region));
    }

    public boolean putAllGeneralsBackToStarMenu() {
        centerNavigationPoint(getFirstNavigationPoint());
        islandCmds.parkMouse();
        List<Match> matchList;
        int maxLoop = 3, loopCounter = 0;
        while ((matchList = findAllReadyGenerals()).size() > 0) {
            for (Match match : matchList) {
                log.info("match: " + match);
                match.hover();
                match.doubleClick();
                islandCmds.sleepX(3);
                // now we have a open General Menu
                generalMenu.putBackToStarMenu();
            }
            ++loopCounter;
        }
        return true;
    }

    protected abstract NavigationPoint getFirstNavigationPoint();

    protected List<Match> findAllReadyGenerals() {
        List<Match> list = new ArrayList<>();
        Iterator<Match> it = islandCmds.findAll(pattern("ready-general-flag.png").similar(0.80).targetOffset(0, 20));
        if (it != null) {
            while (it.hasNext()) {
                list.add(it.next());
            }
        } else {
            log.info("No ready general. Realy?");
        }
        return list;
    }


    public Match findGeneralInStarMenu(GeneralType general, String generalName, boolean shouldWait) {
        log.info("findGeneralInStarMenu() general: " + general + ", generalName: " + generalName + "m shouldWait: " + shouldWait);
        if (generalName == null || generalName.isEmpty()) {
            generalName = GeneralsFilterString.filterString;
        }
        if (!starMenu.openStarMenu(generalName)) {
            throw new IllegalStateException("Missing open star menu");
        }
        Match match = null;
        int loops = (shouldWait ? 40 : 1);

        for (int i = 0; i < loops; ++i) {
            match = islandCmds.find(general.getPattern(), starMenu.getMenuRegion());
            if (match == null) {
                // wait until general is available through multiple loops
                log.info("Waiting on general: " + general + "/" + generalName);
                islandCmds.sleep(15, TimeUnit.SECONDS);
            } else {
                break;
            }
        }
        return match;
    }

    private void markPreviousStepsAsDone(AdventureStep baseStep, int maxIndex) {
        AdventureStep step;
        for (int i = 0; i < maxIndex; ++i) {
            step = this.adventureSteps.get(i);
            if (baseStep.getGeneral().equals(step.getGeneral()) && baseStep.getGeneralName() != null && baseStep.getGeneralName().equals
                    (step.getGeneralName())) {
                step.setState(DONE);
            }
        }
    }

    /**
     * Assumes a open general menu.
     *
     * @param step
     * @return Fehlercode | Bedeutung
     * 0 : alles ok
     * 1 : unbekannter Fehler
     * 2 : camp nicht gefunden
     * 3 : attack preparation failed
     * 4 : Clicking the attack button failed
     * 5 : Clicking attack camp failed
     */
    private int attack(AdventureStep step) {
        log.info("attack()");
        int rv = 0;
        // NavigationPoint navPoint = whereIam();
        if (openGeneralMenu(step.getGeneral(), step.getGeneralName())) {
            if (clickAttackButton()) {
                islandCmds.sleep();
                moveToCamp(step.getStartNavPoint(), step.getTargetNavPoint(), step.getTargetDragDropOffset());
                if (clickAttackCamp(step.getCamp())) {
                    islandCmds.sleepX(2);
                    failIfBuildCancelButtonExists();
                } else {
                    rv = 5;
                    log.severe("Clicking attack camp failed");
                    islandCmds.clickBuildCancelButton();
                }
            } else {
                log.severe("Clicking the attack button failed");
                rv = 4;
            }
        } else {
            log.severe("Failed to open general menu");
            rv = 3;
        }
        return rv;
    }

    private void failIfBuildCancelButtonExists() {
        if (islandCmds.clickBuildCancelButton()) {
            throw new IllegalStateException("Attack failed because cancel button exists");
        }
    }

    public void restoreState() {
        log.info("restoreState()");
        List<AdventureStep> list;
        try {
            AdventureState state = objectMapper.readValue(getFilename(), AdventureState.class);
            list = state.getAdventureSteps();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (list == null) {
            list = new ArrayList<>();
        }
        int i = 0;
        for (AdventureStep item : list) {
            item.setNo(++i);
        }
        this.adventureSteps = list;
    }

    private void saveState(AdventureStep step, AdventureStepState state) {
        step.setState(state);
        this.saveState();
    }

    public void saveState() {
        log.info("saveState()");
        try {
            AdventureState state = new AdventureState();
            state.setAdventureSteps(this.adventureSteps);
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(getFilename(), state);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @return
     */
    protected boolean prepareAttack(AdventureStep step) {
        log.info("prepareAttack() " + step.getCamp());
        GeneralType general = step.getGeneral();
        String generalName = step.getGeneralName();
        AttackUnit[] units = step.getUnits();
        boolean rv = false;
        if (openGeneralMenu(general, generalName)) {
            islandCmds.sleep();
            if (generalMenu.setupAttackUnits(units)) {
                // TODO JPU Implement a method to check the setup
                rv = true;

            } else {
                log.severe("Failed to setup attack units");
            }
        } else {
            log.severe("Failed to open general menu");
        }
        return rv;
    }

    private boolean campExists(AttackCamp camp) {
        log.info("campExists()");
        //        if (camp.getDragNDrop() != null) {
        //            islandCmds.dragDrop(camp.getDragNDrop());
        //        }
        Match match = islandCmds.find(camp.getPattern(), region);
        if (match == null) {
            return false;
        }
        match.hover();
        return true;
    }

    /**
     * This method assumes a open general menu.
     */
    protected boolean clickAttackButton() {
        log.info("clickAttackButton()");
        boolean rv = false;
        if (generalMenu.clickAttackBtn()) {
            rv = true;
        } else {
            log.severe("Missing Attack Btn");
        }
        return rv;
    }

    protected boolean centerNavigationPoint(NavigationPoint navPoint) {
        return centerNavigationPoint(navPoint, null, null);
    }

    protected boolean centerNavigationPoint(NavigationPoint navPoint, @Nullable Dimension targetDragDropOffset, @Nullable Dimension
            targetClickOffset) {
        log.info("centerNavigationPoint() navPoint: " + navPoint);
        boolean rv = false;
        islandCmds.hover(new Location(region.w, region.h));
        Match match = islandCmds.find(navPoint.getPattern(), region);
        if (match != null) {
            Location navPointLocation = getNavPointLocation(match);
            Location regionCenterLocation = getMidpoint();
            Dimension dimension = new Dimension(navPointLocation.x - regionCenterLocation.x, navPointLocation.y - regionCenterLocation.y);
            if (targetDragDropOffset != null) {
                log.info("targetDragDropOffset: " + targetDragDropOffset);
                dimension.width = dimension.width + targetDragDropOffset.width;
                dimension.height = dimension.height + targetDragDropOffset.height;
            }
            islandCmds.dragDrop(dimension);
            if (targetClickOffset != null) {
                islandCmds.parkMouse();
                // Nach DragDrop muss erneut gesucht werden.
                match = islandCmds.find(navPoint.getPattern(), region);
                log.info("match: " + match);
                if (match == null) {
                    throw new IllegalStateException();
                }
                navPointLocation = getNavPointLocation(match);
                log.info("targetClickOffset: " + targetClickOffset);
                Location clickLocation = new Location(navPointLocation.x + targetClickOffset.width, navPointLocation.y +
                        targetClickOffset.height);
                islandCmds.hover(clickLocation);
                islandCmds.doubleClick(clickLocation);
            }
            rv = true;
        } else {
            throw new IllegalStateException("Navigation point not found");
        }
        return rv;
    }

    private Location getNavPointLocation(Match match) {
        Location navPointLocation = new Location(match.x, match.y);
        navPointLocation.x = match.x + (match.w / 2);
        navPointLocation.y = match.y + (match.h / 2);
        return navPointLocation;
    }

    public void hoverRegionCenter() {
        islandCmds.hover(getMidpoint());
    }

    protected Location getMidpoint() {
        return LocationMath.getMidpointLocation(region);
    }

    /**
     * This method assumes a General in Attack-Mode.
     */
    protected boolean clickAttackCamp(AttackCamp camp) {
        log.info("clickAttackCamp");
        boolean rv = false;
        islandCmds.parkMouse();
        islandCmds.sleep();
        Match match = islandCmds.find(camp.getPattern(), region);
        if (match != null) {
            match.hover();
            islandCmds.sleepX(1);
            match.doubleClick(); // Angriff starten
            islandCmds.sleepX(1);
            rv = true;
        } else {
            log.severe("Camp not found: " + camp);
        }
        return rv;
    }

    /**
     * This method assumes a General in Attack-Mode.
     */
    protected void moveToCamp(NavigationPoint startNavPoint, NavigationPoint targetNavPoint, Dimension targetDragDropOffset) {
        Objects.requireNonNull(startNavPoint, "Missing startNavPoint");
        Objects.requireNonNull(targetNavPoint, "Missing targetNavPoint");

        NavigationPoint currentNavPoint = whereIam();
        Objects.requireNonNull(currentNavPoint, "Failed to identify starting point");

        if (!startNavPoint.equals(currentNavPoint)) {
            log.info("Go to expectedStartNavPoint ");
            route(currentNavPoint, startNavPoint, null, null);
            currentNavPoint = whereIam();
        }
        if (!startNavPoint.equals(currentNavPoint)) {
            throw new IllegalStateException("Required starting navigation point not given. expectedStartNavPoint:" +
                    startNavPoint + ", currentNavPoint: " + currentNavPoint);
        }
        route(currentNavPoint, targetNavPoint, targetDragDropOffset, null);
    }

    NavigationPoint whereIam() {
        List<NavigationPoint> navPoints = getNavigationPoints();
        Match match = null;
        for (NavigationPoint navPoint : navPoints) {
            match = islandCmds.find(navPoint.getPattern(), region);
            if (match != null) {
                return navPoint;
            }
        }
        throw new IllegalStateException("The current position is unknown.");
    }

    void processMoveStep(AdventureStep step) {
        log.info("processMoveStep()");
        GeneralType general = step.getGeneral();
        String generalName = step.getGeneralName();
        NavigationPoint navPoint = step.getTargetNavPoint();
        if (openGeneralMenu(general, generalName)) {
            islandCmds.sleepX(5);
            // clickUnsetUnitButton();
            if (generalMenu.clickMoveBtn()) {
                Objects.requireNonNull(step.getTargetNavPointClickOffset());
                route(step.getStartNavPoint(), step.getTargetNavPoint(), step.getTargetDragDropOffset(), step
                        .getTargetNavPointClickOffset());
            } else {
                log.severe("Failed to click processMoveStep button");
            }
        } else {
            log.severe("Open menu failed");
        }
        failIfBuildCancelButtonExists();
    }

    public void initZoom() {
        islandCmds.type("0");
        zoomOut();
    }

    public boolean openGeneralMenu(GeneralType general, String generalName) {
        log.info("openGeneralMenu() general: " + general + ", generalName: " + generalName);
        boolean rv = false;
        Match match = findGeneralInStarMenu(general, generalName, true);
        if (match != null) {
            islandCmds.sleep();
            if (match.click() == 1) {
                islandCmds.sleepX(2);
                rv = true;
            } else {
                log.severe("Click was not successfull");
            }
        } else {
            log.severe("General not found");
        }
        return rv;
    }

    public void zoomOut() {
        islandCmds.type("-");
    }

    public void prepareStarMenu(StarMenuFilter filter) {
        dsoService.prepareStarMenu(filter);
    }

    private boolean processSolveQuestStep(AdventureStep step) {
        log.info("processSolveQuestStep()");
        boolean rv = false;
        int counter = 0;
        for (int i = 0; i < 10; ++i) {
            islandCmds.sleep(10, TimeUnit.SECONDS);
            islandCmds.openQuestBook();
            islandCmds.sleepX(2);
            if (islandCmds.clickSmallOkButton()) {
                ++counter;
            }
            if (counter > 0) {
                rv = true;
                break;
            }
            islandCmds.closeQuestBook();
        }
        return rv;
    }

    private boolean processUnsetUnitsStep(AdventureStep step) {
        log.info("processUnsetUnitsStep()");
        boolean rv = false;
        if (openGeneralMenu(step.getGeneral(), step.getGeneralName())) {
            rv = generalMenu.unsetUnits();
        }
        return rv;
    }
}
