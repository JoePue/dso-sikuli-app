package de.puettner.sikuli.dso.adv;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import de.puettner.sikuli.dso.AppEnvironment;
import de.puettner.sikuli.dso.DSOService;
import de.puettner.sikuli.dso.commands.ui.IslandCommands;
import de.puettner.sikuli.dso.commands.ui.MenuBuilder;
import de.puettner.sikuli.dso.commands.ui.StarMenu;
import de.puettner.sikuli.dso.commands.ui.StarMenuFilter;
import lombok.extern.java.Log;
import org.sikuli.script.Match;
import org.sikuli.script.Region;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import static de.puettner.sikuli.dso.adv.AdventureStepState.*;
import static de.puettner.sikuli.dso.adv.StepType.*;
import static de.puettner.sikuli.dso.commands.ui.SikuliCommands.pattern;
import static de.puettner.sikuli.dso.commands.ui.StarMenuFilter.GeneralsFilterString;

@Log
public abstract class Adventure {

    private static final ObjectMapper objectMapper = new ObjectMapper();
    protected final Region region;
    private final StarMenu starMenu;
    private final DSOService dsoService;
    private final AppEnvironment appEnvironment;
    private final AdventureRouter adventureRouter;
    protected List<AdventureStep> adventureSteps = new ArrayList<>();
    protected IslandCommands islandCmds;
    protected GeneralMenu generalMenu;

    protected Adventure(IslandCommands islandCmds, StarMenu starMenu, DSOService dsoService, AppEnvironment appEnvironment,
                        AdventureRouter adventureRouter) {
        this.islandCmds = islandCmds;
        this.starMenu = starMenu;
        this.generalMenu = MenuBuilder.build().buildGeneralMenu();
        this.region = islandCmds.getIslandRegion();
        this.dsoService = dsoService;
        this.appEnvironment = appEnvironment;
        this.adventureRouter = adventureRouter;

        adventureRouter.fillNavigationPointsList();

        SimpleModule module = new SimpleModule();
        module.addDeserializer(AttackCamp.class, new AttackCampDeserializer());
        objectMapper.registerModule(module);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    protected abstract File getFilename();

    public void play() {
        log.info("play");
        this.restoreState();
        adventureRouter.routeCheck(adventureSteps);
        this.initZoom();
        islandCmds.typeESC();
        islandCmds.closeChat();

        final Map<Integer, Integer> failedSteps = new HashMap<>();
        Optional<AdventureStep> step;
        while ((step = processSteps()).isPresent()) {
            int stepNo = step.get().getNo();
            int failureCount = 0;
            if (failedSteps.containsKey(stepNo)) {
                failureCount = failedSteps.get(stepNo);
            }
            if (failureCount < 3) {
                log.severe("Failed step is processing again. stepNo: " + stepNo);
                failedSteps.put(stepNo, ++failureCount);
                clickAssertButtons();
                islandCmds.sleep(60, TimeUnit.SECONDS);
            } else {
                log.severe("Failed step no longer processed. stepNo: " + stepNo);
                break;
            }
        }
        processFinishAction();
    }

    private void processFinishAction() {
        if (this.adventureSteps != null) {
            for (AdventureStep step : this.adventureSteps) {
                if (FINISH_ACTION.equals(step.getStepType()) && step.getComment() != null) {
                    try {
                        Runtime.getRuntime().exec("cmd /c start " + step.getComment());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }

    private void clickAssertButtons() {
        while (islandCmds.clickAssertOkButton()) {
            // correct
        }
    }

    private Optional<AdventureStep> processSteps() {
        Optional<AdventureStep> rv = Optional.empty();
        AdventureStep step = null;
        boolean supportedStep;
        int processedStepCounter = 0;
        try {
            for (int i = 0; i < this.adventureSteps.size(); ++i) {
                step = this.adventureSteps.get(i);
                supportedStep = false;
                if (!DONE.equals(step.getState())) {
                    log.info(step.toString());
                    confirmSolvedQuest();
                }
                if (FINISH_ACTION.equals(step.getState())) {
                    ++processedStepCounter;
                    supportedStep = true;
                    continue;
                }
                // *** OPEN ***
                if (OPEN.equals(step.getState())) {
                    // *** OPEN - MOVE ***
                    processStepDelay(step, processedStepCounter == 0);
                    if (StepType.ALL_BACK_TO_STAR_MENU.equals(step.getStepType())) {
                        supportedStep = true;
                        if (!processAllGeneralsBackToStarMenuStep()) {
                            throw new IllegalStateException("Failed to process step: " + step);
                        }
                        ++processedStepCounter;
                        saveState(step, DONE);
                    }
                    // *** OPEN - LAND ***
                    if (StepType.LAND.equals(step.getStepType()) && OPEN.equals(step.getState())) {
                        supportedStep = true;
                        if (!processLandStep(step)) {
                            throw new IllegalStateException("Failed to process step: " + step);
                        }
                        ++processedStepCounter;
                        saveState(step, DONE);
                    }
                    // *** OPEN > MOVE ***
                    if (StepType.MOVE.equals(step.getStepType())) {
                        supportedStep = true;
                        Objects.requireNonNull(step.getTargetNavPoint());
                        // Objects.requireNonNull(step.getTargetDragDropOffset());
                        if (OPEN.equals(step.getState())) {
                            processMoveStep(step);
                            ++processedStepCounter;
                            saveState(step, AdventureStepState.DONE);
                        }
                    }
                    // *** OPEN > ATTACK ***
                    if (StepType.ATTACK.equals(step.getStepType())) {
                        supportedStep = true;
                        if (this.prepareAttack(step)) {
                            ++processedStepCounter;
                            saveState(step, PREPARED);
                            islandCmds.typeESC();
                        } else {
                            throw new IllegalStateException("Attack preparation failed");
                        }
                    }
                    // *** OPEN > SOLVE_QUEST ***
                    if (StepType.SOLVE_QUEST.equals(step.getStepType())) {
                        supportedStep = true;
                        if (!processSolveQuestStep(step)) {
                            throw new IllegalStateException("step processing failed");
                        }
                        ++processedStepCounter;
                        saveState(step, DONE);
                    }
                    // *** OPEN > UNSET_UNITS ***
                    if (StepType.UNSET_UNITS.equals(step.getStepType())) {
                        supportedStep = true;
                        if (!processUnsetUnitsStep(step)) {
                            throw new IllegalStateException("step processing failed");
                        }
                        saveState(step, DONE);
                    }
                    // *** OPEN > EXIT_DSO ***
                    if (StepType.EXIT_DSO.equals(step.getStepType())) {
                        supportedStep = true;
                        ++processedStepCounter;
                        saveState(step, DONE);
                        dsoService.exitDso();
                    }
                    // *** OPEN > WAIT ***
                    if (WAIT.equals(step.getStepType())) {
                        supportedStep = true;
                        ++processedStepCounter;
                        saveState(step, DONE);
                    }
                }
                if (PREPARED.equals(step.getState())) {
                    processStepDelay(step, processedStepCounter == 0);
                    // *** PREPARED > ATTACK ***
                    if (StepType.ATTACK.equals(step.getStepType())) {
                        supportedStep = true;
                        int errorCode = attack(step);
                        if (errorCode == 0) {
                            saveState(step, DONE);
                            markPreviousStepsAsDone(step, i);
                        } else if (errorCode == 5) {
                            throw new IllegalStateException("attack step failed");
                        } else {
                            throw new IllegalStateException("attack step failed");
                        }
                        ++processedStepCounter;
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
            log.info("current stepNo: " + step.getNo());
            log.log(Level.SEVERE, e.getMessage(), e);
            rv = Optional.ofNullable(step);
        } finally {
            saveState();
        }
        return rv;
    }

    private void confirmSolvedQuest() {
        dsoService.confirmSolvedQuest();
        dsoService.confirmNewQuest();
        islandCmds.closeQuestBook();
    }

    protected void processStepDelay(AdventureStep step, boolean isFirst) {
        if (isDelay(isFirst, step)) {
            log.info("processStepDelay()");
            islandCmds.sleep(step.getDelay(), TimeUnit.SECONDS);
        }
    }

    protected boolean isDelay(boolean isFirst, AdventureStep step) {
        if (step.getDelay() != null && step.getDelay() > 0) {
            if (!isFirst || WAIT.equals(step.getStepType())) {
                if (ATTACK.equals(step.getStepType()) && OPEN.equals(step.getState())) {
                    return false;
                }
                return true;
            }
        }
        return false;
    }

    private boolean processLandStep(AdventureStep step) {
        log.info("processLandStep");
        adventureRouter.route(adventureRouter.whereIam(), getFirstNavigationPoint(), null, null);
        adventureRouter.centerNavigationPoint(getFirstNavigationPoint());
        boolean rv = false;
        if (this.openGeneralMenu(step.getGeneral(), step.getGeneralName())) {
            islandCmds.sleep();
            Optional<Match> match = this.findLandingLocation();
            if (match.isPresent()) {
                match.get().hover();
                match.get().doubleClick();
                rv = true;
            } else {
                if (islandCmds.clickBuildCancelButton()) {
                    throw new IllegalStateException("Missing landing location");
                } else {
                    generalMenu.closeMenu();
                    log.info("General already landed.");
                    rv = true;
                }
            }
        }
        return rv;
    }

    private void highlightRegion() {
        this.region.highlight(3, "green");
    }

    private Optional<Match> findLandingLocation() {
        return Optional.ofNullable(islandCmds.find((pattern("landing-location-1-zoom1.png").similar(0.90f)), region));
    }

    public boolean processAllGeneralsBackToStarMenuStep() {
        log.info("processAllGeneralsBackToStarMenuStep");
        adventureRouter.centerNavigationPoint(getFirstNavigationPoint());
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
        Iterator<Match> it = islandCmds.findAll(pattern("ready-general-flag.png").similar(0.72f).targetOffset(0, 20));
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
        return isGeneralAvailable(general, generalName, shouldWait);
    }

    private Match isGeneralAvailable(GeneralType general, String generalName, boolean shouldWait) {
        Match match = null;
        int loops = (shouldWait ? 40 : 1);

        for (int i = 0; i < loops; ++i) {
            match = islandCmds.find(general.getPattern(), starMenu.getMenuRegion());
            if (match == null) {
                // wait until general is available through multiple loops
                log.info("Waiting on general: " + general + "/" + generalName);
                islandCmds.sleep(15, TimeUnit.SECONDS);
                dsoService.preventScreensaver();
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
                adventureRouter.moveToCamp(step.getStartNavPoint(), step.getTargetNavPoint(), step.getTargetDragDropOffset(), step
                        .getInitialDragDropOffset());
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
            throw new IllegalStateException("cancel button must not exists");
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
            JsonFileFormatter.format(getFilename());
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

    public void hoverRegionCenter() {
        islandCmds.hover(adventureRouter.getMidpoint());
    }

    /**
     * This method assumes a General in Attack-Mode.
     */
    protected boolean clickAttackCamp(AttackCamp camp) {
        log.info("clickAttackCamp");
        boolean rv = false;
        Match match = findCamp(camp);
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

    private Match findCamp(AttackCamp camp) {
        Match match = null;
        islandCmds.parkMouseInLeftUpperCorner();
        match = islandCmds.find(camp.getPattern(), region);
        if (match == null) {
            islandCmds.parkMouseInLeftLowerCorner();
            match = islandCmds.find(camp.getPattern(), region);
        }
        if (match == null) {
            islandCmds.parkMouseInRightUpperCorner();
            match = islandCmds.find(camp.getPattern(), region);
        }
        if (match == null) {
            islandCmds.parkMouseInRightLowerCorner();
            match = islandCmds.find(camp.getPattern(), region);
        }
        return match;
    }

    void processMoveStep(AdventureStep step) {
        log.info("processMoveStep()");
        GeneralType general = step.getGeneral();
        String generalName = step.getGeneralName();
        NavigationPoint navPoint = step.getTargetNavPoint();
        if (openGeneralMenu(general, generalName)) {
            islandCmds.sleepX(5);
            if (generalMenu.clickMoveBtn()) {
                Objects.requireNonNull(step.getTargetNavPointClickOffset());
                if (step.getInitialDragDropOffset() != null) {
                    islandCmds.dragDrop(step.getInitialDragDropOffset());
                }
                adventureRouter.route(step.getStartNavPoint(), step.getTargetNavPoint(), step.getTargetDragDropOffset(), step
                        .getTargetNavPointClickOffset());
                islandCmds.sleepX(1);
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
        for (int i = 0; i < 50; ++i) {
            if (dsoService.confirmSolvedQuest()) {
                islandCmds.sleepX(2);
                dsoService.confirmNewQuest();
                break;
            } else {
                islandCmds.sleep(15, TimeUnit.SECONDS);
            }
        }
        return true;
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
