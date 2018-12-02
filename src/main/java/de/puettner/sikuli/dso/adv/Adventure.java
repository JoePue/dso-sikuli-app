package de.puettner.sikuli.dso.adv;

import de.puettner.sikuli.dso.AppEnvironment;
import de.puettner.sikuli.dso.DSOService;
import de.puettner.sikuli.dso.adv.step_proc.AttackStepProcessor;
import de.puettner.sikuli.dso.adv.step_proc.CommonStepProcessor;
import de.puettner.sikuli.dso.commands.ui.IslandCommands;
import de.puettner.sikuli.dso.commands.ui.MenuBuilder;
import de.puettner.sikuli.dso.commands.ui.StarMenu;
import lombok.extern.java.Log;
import org.sikuli.script.Match;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import static de.puettner.sikuli.dso.adv.AdventureStepState.*;
import static de.puettner.sikuli.dso.adv.StepType.FINISH_ACTION;
import static de.puettner.sikuli.dso.adv.StepType.WAIT;
import static de.puettner.sikuli.dso.commands.ui.SikuliCommands.pattern;

@Log
public abstract class Adventure {

    public final String adventureFilename;
    protected final FileService fileService;
    private final DSOService dsoService;
    private final AdventureRouter adventureRouter;
    private final IslandCommands islandCmds;
    private final AttackStepProcessor attackStepProcessor;
    private final CommonStepProcessor cmnStepProcessor;
    protected List<AdventureStep> adventureSteps = new ArrayList<>();
    private AdventureConfiguration configuration;

    protected Adventure(IslandCommands islandCmds, StarMenu starMenu, DSOService dsoService, AdventureRouter adventureRouter, String
            adventureFilename) {
        this.adventureFilename = adventureFilename;
        this.adventureRouter = adventureRouter;
        this.islandCmds = islandCmds;
        this.dsoService = dsoService;
        this.fileService = new FileService(AppEnvironment.getInstance().appendFilename(adventureFilename));
        this.cmnStepProcessor = new CommonStepProcessor(islandCmds, MenuBuilder.build().buildGeneralMenu(), adventureRouter, starMenu,
                dsoService);
        this.attackStepProcessor = new AttackStepProcessor(cmnStepProcessor);
    }

    public void play() {
        log.info("play");
        this.restoreState();
        adventureRouter.routeCheck(adventureSteps);
        initZoom();
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
                log.severe("Failed to process step. stepNo: " + stepNo);
                failedSteps.put(stepNo, ++failureCount);
                // Rollback actions
                clickAssertButtons();
                islandCmds.clickBuildCancelButton();
                dsoService.confirmNewQuest();
                islandCmds.sleep(30, TimeUnit.SECONDS);
            } else {
                log.severe("Failed step is not longer processed. stepNo: " + stepNo);
                break;
            }
        }
        processFinishAction();
    }

    public void restoreState() {
        log.info("restoreState()");
        AdventureState state = fileService.restoreState();
        this.adventureSteps = state.getAdventureSteps();
        this.configuration = state.getConfiguration();
    }

    private void initZoom() {
        islandCmds.initZoom();
        islandCmds.zoomOut();
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
                    cmnStepProcessor.processStepDelay(step, processedStepCounter == 0);
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

                        if (OPEN.equals(step.getState())) {
                            cmnStepProcessor.processMoveStep(step);
                            ++processedStepCounter;
                            saveState(step, AdventureStepState.DONE);
                        }
                    }
                    // *** OPEN > SOLVE_QUEST ***
                    if (StepType.SOLVE_QUEST.equals(step.getStepType())) {
                        supportedStep = true;
                        if (!cmnStepProcessor.processSolveQuestStep(step)) {
                            throw new IllegalStateException("step processing failed");
                        }
                        ++processedStepCounter;
                        saveState(step, DONE);
                    }
                    // *** OPEN > UNSET_UNITS ***
                    if (StepType.UNSET_UNITS.equals(step.getStepType())) {
                        supportedStep = true;
                        if (!cmnStepProcessor.processUnsetUnitsStep(step)) {
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
                    // *** OPEN > ATTACK ***
                    if (StepType.ATTACK.equals(step.getStepType())) {
                        supportedStep = true;
                        if (attackStepProcessor.prepareAttack(step, Optional.ofNullable(configuration.getRedoOneManSetup()))) {
                            saveState(step, PREPARED);
                            int errorCode = attackStepProcessor.attack(step);
                            processAttackReturnCode(step, i, errorCode);
                            ++processedStepCounter;
                        } else {
                            throw new IllegalStateException("Attack preparation failed");
                        }
                    }
                }
                if (PREPARED.equals(step.getState())) {
                    cmnStepProcessor.processStepDelay(step, processedStepCounter == 0);
                    // *** PREPARED > ATTACK ***
                    if (StepType.ATTACK.equals(step.getStepType())) {
                        supportedStep = true;
                        int errorCode = attackStepProcessor.processAttackStep(step);
                        processAttackReturnCode(step, i, errorCode);
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

    private void clickAssertButtons() {
        while (islandCmds.clickAssertOkButton()) {
            // correct
        }
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

    private void confirmSolvedQuest() {
        dsoService.confirmSolvedQuest();
        dsoService.confirmNewQuest();
        islandCmds.closeQuestBook();
    }

    public boolean processAllGeneralsBackToStarMenuStep() {
        log.info("processAllGeneralsBackToStarMenuStep");
        adventureRouter.centerNavigationPoint(getFirstNavigationPoint());
        return cmnStepProcessor.processAllGeneralsBackToStarMenuStep();
    }

    private void saveState(AdventureStep step, AdventureStepState state) {
        step.setState(state);
        this.saveState();
    }

    private boolean processLandStep(AdventureStep step) {
        log.info("processLandStep");
        adventureRouter.route(adventureRouter.whereIam(), getFirstNavigationPoint(), null, null, step.getInitialDragDropOffset());
        adventureRouter.centerNavigationPoint(getFirstNavigationPoint());
        boolean rv = false;
        if (cmnStepProcessor.openGeneralMenu(step.getGeneral(), step.getGeneralName(), step.getState())) {
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
                    cmnStepProcessor.closeMenu();
                    log.info("General already landed.");
                    rv = true;
                }
            }
        }
        return rv;
    }

    private void processAttackReturnCode(AdventureStep step, int i, int errorCode) {
        if (errorCode == 0) {
            saveState(step, DONE);
            markPreviousStepsAsDone(step, i);
        } else if (errorCode == 5) {
            throw new IllegalStateException("processAttackStep step failed");
        } else {
            throw new IllegalStateException("processAttackStep step failed");
        }
    }

    public void saveState() {
        log.info("saveState()");
        AdventureState state = new AdventureState();
        state.setAdventureSteps(this.adventureSteps);
        state.setConfiguration(this.configuration);
        fileService.saveState(state);
    }

    protected abstract NavigationPoint getFirstNavigationPoint();

    private Optional<Match> findLandingLocation() {
        return Optional.ofNullable(islandCmds.find((pattern("landing-location-1-zoom1.png").similar(0.90f)), islandCmds.getIslandRegion()));
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

    protected AttackStepProcessor getAttackStepProcessor() {
        return attackStepProcessor;
    }

    public CommonStepProcessor getCommonStepProcessor() {
        return cmnStepProcessor;
    }
}
