package de.puettner.sikuli.dso.adv.step_proc;

import de.puettner.sikuli.dso.DSOService;
import de.puettner.sikuli.dso.adv.*;
import de.puettner.sikuli.dso.commands.ui.IslandCommands;
import de.puettner.sikuli.dso.commands.ui.StarMenu;
import de.puettner.sikuli.dso.commands.ui.StarMenuFilter;
import lombok.extern.java.Log;
import org.sikuli.script.Match;
import org.sikuli.script.Region;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static de.puettner.sikuli.dso.adv.AdventureStepState.OPEN;
import static de.puettner.sikuli.dso.adv.StepType.ATTACK;
import static de.puettner.sikuli.dso.adv.StepType.WAIT;
import static de.puettner.sikuli.dso.commands.ui.SikuliCommands.pattern;
import static de.puettner.sikuli.dso.commands.ui.StarMenuFilter.GeneralsFilterString;

@Log
public class CommonStepProcessor {

    private final IslandCommands islandCmds;
    private final Region region;
    private final StarMenu starMenu;
    private final DSOService dsoService;
    private final GeneralMenu generalMenu;
    private final AdventureRouter adventureRouter;

    public CommonStepProcessor(IslandCommands islandCmds, GeneralMenu generalMenu, AdventureRouter adventureRouter,
                               StarMenu starMenu, DSOService dsoService) {
        this.islandCmds = islandCmds;
        this.generalMenu = generalMenu;
        this.adventureRouter = adventureRouter;
        this.region = islandCmds.getIslandRegion();
        this.starMenu = starMenu;
        this.dsoService = dsoService;
    }

    public AdventureRouter getAdventureRouter() {
        return adventureRouter;
    }

    public IslandCommands getIslandCmds() {
        return islandCmds;
    }

    public boolean clickAttackBtn() {
        return generalMenu.clickAttackBtn();
    }

    public Region getRegion() {
        return region;
    }

    public GeneralMenu getGeneralMenu() {
        return generalMenu;
    }

    public Match findCamp(AttackCamp camp) {
        Match match;
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

    public void closeMenu() {
        generalMenu.closeMenu();
    }

    private void highlightRegion() {
        this.region.highlight(3, "green");
    }

    public boolean processUnsetUnitsStep(AdventureStep step) {
        log.info("processUnsetUnitsStep()");
        boolean rv = false;
        if (openGeneralMenu(step.getGeneral(), step.getGeneralName(), step.getState())) {
            rv = generalMenu.unsetUnits();
        }
        return rv;
    }

    public boolean openGeneralMenu(GeneralType general, String generalName, AdventureStepState state) {
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

    public void hoverRegionCenter() {
        islandCmds.hover(adventureRouter.getMidpoint());
    }

    public void processMoveStep(AdventureStep step) {
        log.info("processMoveStep()");
        GeneralType general = step.getGeneral();
        String generalName = step.getGeneralName();
        NavigationPoint navPoint = step.getTargetNavPoint();
        if (openGeneralMenu(general, generalName, step.getState())) {
            islandCmds.sleepX(5);
            if (generalMenu.clickMoveBtn()) {
                Objects.requireNonNull(step.getTargetNavPointClickOffset());
                if (step.getInitialDragDropOffset() != null) {
                    islandCmds.dragDrop(step.getInitialDragDropOffset());
                }
                adventureRouter.route(step.getStartNavPoint(), step.getTargetNavPoint(), step.getTargetDragDropOffset(), step
                        .getTargetNavPointClickOffset(), step.getInitialDragDropOffset());
                islandCmds.sleepX(1);
            } else {
                log.severe("Failed to click processMoveStep button");
            }
        } else {
            log.severe("Open menu failed");
        }
        failIfBuildCancelButtonExists();
    }

    protected void failIfBuildCancelButtonExists() {
        if (islandCmds.clickBuildCancelButton()) {
            throw new IllegalStateException("cancel button must not exists");
        }
    }

    public void prepareStarMenu(StarMenuFilter filter) {
        dsoService.prepareStarMenu(filter);
    }

    public boolean processSolveQuestStep(AdventureStep step) {
        log.info("processSolveQuestStep()");
        final int SLEEP_TIME = 15;
        final int MAX_TIME = 300 / SLEEP_TIME;

        for (int i = 0; i < MAX_TIME; ++i) {
            if (dsoService.confirmSolvedQuest()) {
                islandCmds.sleepX(2);
                dsoService.confirmNewQuest();
                break;
            } else {
                islandCmds.sleep(SLEEP_TIME, TimeUnit.SECONDS);
            }
        }
        return true;
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

    public boolean processAllGeneralsBackToStarMenuStep() {
        islandCmds.parkMouse();
        int maxLoop = 3, loopCounter = 0;
        List<Match> matchList = findAllReadyGenerals();
        while (matchList.size() > 0) {
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

    public void processStepDelay(AdventureStep step, boolean isFirst) {
        if (isDelay(isFirst, step)) {
            log.info("processStepDelay()");
            islandCmds.sleep(step.getDelay(), TimeUnit.SECONDS);
        }
    }


    public boolean isDelay(boolean isFirst, AdventureStep step) {
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
}
