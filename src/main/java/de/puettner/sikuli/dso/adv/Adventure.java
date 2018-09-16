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

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;

@Log
public abstract class Adventure {

    private static final ObjectMapper objectMapper = new ObjectMapper();
    protected final Region region;
    protected final List<NavigationPoint> navPoints = new ArrayList<>();
    private final StarMenu starMenu;
    private final DSOService dsoService;
    protected List<AdventureAttackStep> adventureSteps = new ArrayList<>();
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

    public abstract void route(NavigationPoint startingPoint, NavigationPoint destinationPoint, Dimension targetOffset);

    public abstract List<NavigationPoint> getNavigationPoints();

    protected abstract File getFilename();

    public void play() {
        log.info("play");
        this.restoreState();
        islandCmds.typeESC();
        try {
            AdventureAttackStep step;
            for (int i = 0; i < this.adventureSteps.size(); ++i) {
                step = this.adventureSteps.get(i);
                if (AdventureStepState.PENDING.equals(step.getState())) {
                    if (StepType.ATTACK.equals(step.getStepType())) {
                        if (waitUntilGeneralIsAvailable(step.getGeneral(), step.getGeneralName())) {
                            saveState(step, AdventureStepState.DONE);
                        } else {
                            throw new IllegalStateException("Missing available general " + step.getGeneral() + ", " + step.getGeneralName
                                    ());
                        }
                    } else {
                        throw new IllegalStateException("Unsupported type: " + step.getStepType());
                    }
                }
            }
            for (int i = 0; i < this.adventureSteps.size(); ++i) {
                step = this.adventureSteps.get(i);
                if (StepType.ATTACK.equals(step.getStepType())) {
                    // prepareAttack
                    if (AdventureStepState.OPEN.equals(step.getState())) {
                        this.prepareAttack(step);
                        saveState(step, AdventureStepState.PREPARED);
                        islandCmds.typeESC();
                    }
                    if (AdventureStepState.PREPARED.equals(step.getState())) {
                        log.info(step.toString());
                        int errorCode = attack(step);

                        if (errorCode == 0) {
                            saveState(step, AdventureStepState.PENDING);
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
            }
            log.info("All adventure steps processed");
        } catch (Exception e) {
            log.log(Level.SEVERE, e.getMessage(), e);
            // e.printStackTrace();
        } finally {
            saveState();
        }
    }

    private boolean waitUntilGeneralIsAvailable(GeneralType general, String generalName) {
        boolean rv = true;
        for (int i = 0; i < 10; ++i) {
            if (this.findGeneralInStarMenu(general, generalName) == null) {
                islandCmds.sleepX(120);
            } else {
                break;
            }
        }
        islandCmds.typeESC();
        return rv;
    }

    private void markPreviousStepsAsDone(AdventureAttackStep baseStep, int maxIndex) {
        AdventureAttackStep step;
        for (int i = 0; i < maxIndex; ++i) {
            step = this.adventureSteps.get(i);
            if (baseStep.getGeneral().equals(step.getGeneral()) && baseStep.getGeneralName() != null && baseStep.getGeneralName().equals
                    (step.getGeneralName())) {
                step.setState(AdventureStepState.DONE);
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
    private int attack(AdventureAttackStep step) {
        log.info("attack()");
        int rv = 0;
        NavigationPoint navPoint = whereIam();
        if (openGeneralMenu(step.getGeneral(), step.getGeneralName())) {
            if (clickAttackButton()) {
                islandCmds.sleep();
                moveToCamp(step.getCamp(), step.getStartNavPoint());
                if (clickAttackCamp(step.getCamp())) {
                    islandCmds.sleepX(2);
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

    public void restoreState() {
        log.info("restoreState()");
        List<AdventureAttackStep> list;
        try {
            //            objectMapper.enableDefaultTyping();
            //            TypeReference typeRef = new TypeReference<ArrayList<AdventureAttackStep>>() {};
            AdventureState state = objectMapper.readValue(getFilename(), AdventureState.class);
            list = state.getAdventureSteps();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (list == null) {
            list = new ArrayList<>();
        }
        this.adventureSteps = list;
    }

    private void saveState(AdventureAttackStep step, AdventureStepState state) {
        step.setState(state);
        this.saveState();
    }

    public void saveState() {
        log.info("saveState()");
        try {
            AdventureState state = new AdventureState();
            state.setAdventureSteps(this.adventureSteps);
            objectMapper.writeValue(getFilename(), state);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @return
     */
    protected boolean prepareAttack(AdventureAttackStep step) {
        AttackCamp camp = step.getCamp();
        GeneralType general = step.getGeneral();
        String generalName = step.getGeneralName();
        AttackUnit[] units = step.getUnits();
        log.info("prepareAttack() " + camp);
        boolean rv = false;
        if (openGeneralMenu(general, generalName)) {
            rv = generalMenu.setupAttackUnits(units);
            // TODO JPU Implement a method to check the setup
        } else {
            log.severe("Failed to open general menu");
            rv = false;
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
        log.info("centerNavigationPoint()");
        log.info("centerNavigationPoint() navPoint: " + navPoint);
        boolean rv = false;
        Match match = islandCmds.find(navPoint.getPattern(), region);
        if (match != null) {
            Location navPointLocation = new Location(match.x, match.y);
            navPointLocation.x = match.x + (match.w / 2);
            navPointLocation.y = match.y + (match.h / 2);
            Location regionCenterLocation = getMidpoint();
            // System.out.println("match: x=" + match.x + ", y=" + match.y + ", w=" + match.w + ", h=" + match.h);
            Dimension dimension = new Dimension(navPointLocation.x - regionCenterLocation.x, navPointLocation.y - regionCenterLocation.y);
            islandCmds.dragDrop(dimension);
            rv = true;
        } else {
            throw new IllegalStateException("Navigation point not found");
        }
        return rv;
    }

    protected Location getMidpoint() {
        return LocationMath.getMidpointLocation(region);
    }

    public void hoverRegionCenter() {
        islandCmds.hover(getMidpoint());
    }

    /**
     * This method assumes a General in Attack-Mode.
     */
    protected boolean clickAttackCamp(AttackCamp camp) {
        log.info("clickAttackCamp");
        boolean rv = false;
        islandCmds.parkMouse();
        Match match = islandCmds.find(camp.getPattern(), region);
        if (match != null) {
            match.hover();
            islandCmds.sleepX(2);
            match.doubleClick(); // Angriff starten
            islandCmds.sleepX(2);
            rv = true;
        } else {
            log.severe("Camp not found: " + camp);
        }
        return rv;
    }

    /**
     * This method assumes a General in Attack-Mode.
     */
    protected void moveToCamp(AttackCamp camp, NavigationPoint expectedStartNavPoint) {
        Objects.requireNonNull(camp, "Missing camp");
        Objects.requireNonNull(camp.getNavigationPoint(), "Missing destination");

        NavigationPoint currentNavPoint = whereIam();
        Objects.requireNonNull(currentNavPoint, "Failed to identify starting point");

        if (!expectedStartNavPoint.equals(currentNavPoint)) {
            log.info("Go to expectedStartNavPoint ");
            route(currentNavPoint, expectedStartNavPoint, null);
            currentNavPoint = whereIam();
        }
        if (!expectedStartNavPoint.equals(currentNavPoint)) {
            throw new IllegalStateException("Required starting navigation point not given. expectedStartNavPoint:" +
                    expectedStartNavPoint + ", currentNavPoint: " + currentNavPoint);
        }
        route(currentNavPoint, camp.getNavigationPoint(), camp.getTargetOffset());
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

    void moveGeneral(GeneralType general, String generalName, NavigationPoint navPoint, Location moveOffset) {
        gotoPosOneAndZoomOut();
        if (openGeneralMenu(general, generalName)) {
            islandCmds.sleepX(5);
            // unsetAllUnits();
            if (generalMenu.clickMoveBtn()) {
                gotoPosOneAndZoomOut();

                // islandCmds.dragDrop(navPoint.getDragNDrop());
                // Find ref point and place Gen
                Match match = islandCmds.find(navPoint.getPattern(), region);
                if (match == null) {
                    log.warning("Move point not found");
                    islandCmds.sleepX(10);
                }
                match = islandCmds.find(navPoint.getPattern(), region);
                if (match == null) {
                    throw new IllegalStateException("Move point not found");
                }
                Location moveLocation = new Location(match.x + (match.w / 2) + moveOffset.x, match.y + (match.h / 2) + moveOffset.y);
                islandCmds.hover(moveLocation);
                islandCmds.click(moveLocation);
            } else {
                log.severe("Failed to click moveGeneral button");
            }
        } else {
            log.severe("Open menu failed");
        }
    }

    @Deprecated
    public void gotoPosOneAndZoomOut() {
        throw new IllegalStateException("Calling this method is not allowed");
        //        islandCmds.type("0");
        //        zoomOut();
    }

    public boolean openGeneralMenu(GeneralType general, String generalName) {
        boolean rv = false;
        Match match = findGeneralInStarMenu(general, generalName);
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
        return true;
    }

    public Match findGeneralInStarMenu(GeneralType general, String generalName) {
        if (!starMenu.openStarMenu(generalName)) {
            throw new IllegalStateException("Missing open star menu");
        }
        return islandCmds.find(general.getPattern(), starMenu.getMenuRegion());
    }

    public void zoomOut() {
        islandCmds.type("-");
        //        islandCmds.sleep();
        //        islandCmds.stepType("-");
        //        islandCmds.sleep();
        //        islandCmds.stepType("-");
        //        islandCmds.sleep();
    }

    private boolean unsetAllUnits() {
        return generalMenu.unsetAllUnits();
    }

    public void prepareStarMenu(StarMenuFilter filter) {
        dsoService.prepareStarMenu(filter);
    }
}
