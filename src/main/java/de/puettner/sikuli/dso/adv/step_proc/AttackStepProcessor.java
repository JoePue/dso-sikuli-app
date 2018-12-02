package de.puettner.sikuli.dso.adv.step_proc;

import de.puettner.sikuli.dso.adv.*;
import de.puettner.sikuli.dso.commands.ui.IslandCommands;
import lombok.extern.java.Log;
import org.sikuli.script.Match;

import java.util.List;

@Log
public class AttackStepProcessor {

    private final CommonStepProcessor commonStepProcessor;
    private final AdventureRouter adventureRouter;
    private final IslandCommands islandCmds;

    public AttackStepProcessor(CommonStepProcessor commonStepProcessor) {
        this.commonStepProcessor = commonStepProcessor;
        adventureRouter = commonStepProcessor.getAdventureRouter();
        islandCmds = commonStepProcessor.getIslandCmds();
    }

    public boolean prepareAttack(AdventureStep step) {
        log.info("prepareAttack() " + step.getCamp());
        GeneralType general = step.getGeneral();
        String generalName = step.getGeneralName();
        AttackUnit[] units = step.getUnits();
        boolean rv = false;
        if (commonStepProcessor.openGeneralMenu(general, generalName, step.getState())) {
            islandCmds.sleep();
            if (commonStepProcessor.getGeneralMenu().setupAttackUnits(units)) {
                rv = true;
            } else {
                log.severe("Failed to setup processAttackStep units");
            }
        } else {
            log.severe("Failed to open general menu");
        }
        return rv;
    }

    /**
     * Assumes a open general menu.
     *
     * @param step
     * @return Fehlercode | Bedeutung
     * 0 : alles ok
     * 1 : unbekannter Fehler
     * 2 : camp nicht gefunden
     * 3 : processAttackStep preparation failed
     * 4 : Clicking the processAttackStep button failed
     * 5 : Clicking processAttackStep camp failed
     */
    public int processAttackStep(AdventureStep step) {
        log.info("processAttackStep()");
        int rv;
        if (commonStepProcessor.openGeneralMenu(step.getGeneral(), step.getGeneralName(), step.getState())) {
            rv = attack(step);
        } else {
            log.severe("Failed to open general menu");
            rv = 3;
        }
        return rv;
    }

    public int attack(AdventureStep step) {
        int rv = 0;
        if (clickAttackButton()) {
            islandCmds.sleep();
            adventureRouter.moveToCamp(step.getStartNavPoint(), step.getTargetNavPoint(), step.getTargetDragDropOffset(), step
                    .getInitialDragDropOffset());
            if (clickAttackCamp(step.getCamp())) {
                islandCmds.sleepX(2);
                commonStepProcessor.failIfBuildCancelButtonExists();
            } else {
                rv = 5;
                log.severe("Clicking processAttackStep camp failed");
                islandCmds.clickBuildCancelButton();
            }
        } else {
            log.severe("Clicking the processAttackStep button failed");
            rv = 4;
        }
        return rv;
    }

    /**
     * This method assumes a open general menu.
     */
    protected boolean clickAttackButton() {
        log.info("clickAttackButton()");
        boolean rv = false;
        if (commonStepProcessor.clickAttackBtn()) {
            rv = true;
        } else {
            log.severe("Missing Attack Btn");
        }
        return rv;
    }

    /**
     * This method assumes a General in Attack-Mode.
     */
    public boolean clickAttackCamp(AttackCamp camp) {
        log.info("clickAttackCamp");
        boolean rv = false;
        Match match = commonStepProcessor.findCamp(camp);
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
     * @param adventureSteps  list of adventure steps
     * @param idx             index of current step in list
     * @param redoOneManSetup
     * @return
     */
    public boolean isPreparationRequired(List<AdventureStep> adventureSteps, int idx, Boolean redoOneManSetup) {
        log.info("isPreparationRequired()");
        AdventureStep currentStep = adventureSteps.get(idx);
        if (redoOneManSetup != null && redoOneManSetup == false && idx < adventureSteps.size() && currentStep.getGeneralName() != null &&
                currentStep.getUnits() != null && currentStep.getUnits().length == 1) {
            for (int i = idx - 1; i >= 0; --i) {
                AdventureStep pastStep = adventureSteps.get(i);
                if (currentStep.getGeneralName().equalsIgnoreCase(pastStep.getGeneralName())) {

                    if (StepType.UNSET_UNITS.equals(pastStep.getStepType())) {
                        return true;
                    }
                    if (StepType.ATTACK.equals(pastStep.getStepType()) && AdventureStepState.DONE.equals(pastStep.getState())) {
                        return !AttackUnit.compare(pastStep.getUnits(), currentStep.getUnits());
                    }
                }
            }
        }
        return true;
    }
}
