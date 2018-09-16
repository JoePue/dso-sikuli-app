package de.puettner.sikuli.dso.adv;

import de.puettner.sikuli.dso.DSOService;
import de.puettner.sikuli.dso.commands.ui.IslandCommands;
import de.puettner.sikuli.dso.commands.ui.StarMenu;
import de.puettner.sikuli.dso.commands.ui.StarMenuFilter;
import lombok.extern.java.Log;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static de.puettner.sikuli.dso.adv.AdventureAttackStep.buildAttackStep;
import static de.puettner.sikuli.dso.adv.AdventureStepState.DONE;
import static de.puettner.sikuli.dso.adv.AdventureStepState.OPEN;
import static de.puettner.sikuli.dso.adv.AttackUnit.*;
import static de.puettner.sikuli.dso.adv.BraveTailorAttackCamp.*;
import static de.puettner.sikuli.dso.adv.BraveTailorNavPoints.NP_1;
import static de.puettner.sikuli.dso.adv.BraveTailorNavPoints.NP_2;
import static de.puettner.sikuli.dso.adv.GeneralType.*;

/**
 * brave tailor = tapferes Schneiderlein
 */
@Log
public class BraveTailorAdv extends Adventure {

    private final File stateFile = new File("brave-tailor-adventure.json");

    /**
     * C'tor
     */
    protected BraveTailorAdv(IslandCommands islandCmds, StarMenu starMenu, DSOService dsoService) {
        super(islandCmds, starMenu, dsoService);
    }

    @Override
    protected void fillNavigationPointsList() {
        log.info("fillNavigationPointsList()");
        for (NavigationPoint np : BraveTailorNavPoints.values()) {
            navPoints.add(np);
        }
    }

    @Override
    public void route(NavigationPoint startingPoint, NavigationPoint targetPoint, Dimension targetOffset) {
        log.info("route() " + startingPoint + " -> " + targetPoint);
        Objects.requireNonNull(startingPoint, "startingPoint is null");
        Objects.requireNonNull(targetPoint, "targetPoint is null");
        log.info("route() startingPoint: " + startingPoint);
        log.info("route() targetPoint: " + targetPoint);
        log.info("route() Dimension: " + targetOffset);

        centerNavigationPoint(startingPoint);
        if (startingPoint.getId().equals(2) && targetPoint.getId().equals(3)) {
            navigate(targetPoint, new Dimension(0, -600), targetOffset);
        } else if (startingPoint.getId().equals(3) && targetPoint.getId().equals(2)) {
            navigate(targetPoint, new Dimension(0, 600), targetOffset);
        } else {
            throw new IllegalStateException("Navigation from " + startingPoint.getId() + " to " + targetPoint.getId() + " is not " +
                    "possible");
        }
    }

    private void navigate(NavigationPoint destinationPoint, Dimension target, Dimension targetOffset) {
        log.info("navigate()");
        islandCmds.dragDrop(target);
        centerNavigationPoint(destinationPoint);
        if (targetOffset != null) {
            islandCmds.dragDrop(new Dimension(targetOffset.width, targetOffset.height));
        }
    }

    @Override
    public List<NavigationPoint> getNavigationPoints() {
        return navPoints;
    }

    @Override
    protected File getFilename() {
        return stateFile;
    }

    private void prepareStarMenu() {
        super.prepareStarMenu(StarMenuFilter.BraveTailor);
    }

    @Deprecated
    public void saveBraveTailorInitState() {
        List<AdventureAttackStep> adventureSteps = new ArrayList<>();
        // sektor 1
        adventureSteps.add(buildAttackStep(NP_1, CAMP_1, Vargus, null, 0, DONE, Rek(120), Kan(75)));
        adventureSteps.add(buildAttackStep(NP_1, CAMP_2, Nusala, null, 10, DONE, Rek(65), Cav(100)));
        adventureSteps.add(buildAttackStep(NP_1, CAMP_3, Generalmajor, null, 10, DONE, Rek(200), Kan(85)));
        adventureSteps.add(buildAttackStep(NP_1, CAMP_4, Vargus, null, 10, DONE, Rek(140), Sol(30), Kan(25)));

        adventureSteps.add(buildAttackStep(NP_1, CAMP_5, MdK, "MDK 1", 1, DONE, Rek(1)));
        adventureSteps.add(buildAttackStep(NP_1, CAMP_5, MdK, "MDK 2", 1, DONE, Rek(1)));
        adventureSteps.add(buildAttackStep(NP_1, CAMP_5, MdK, "MDK 3", 1, DONE, Rek(1)));
        adventureSteps.add(buildAttackStep(NP_1, CAMP_5, Mary, null, 30, DONE, Bos(160), Kan(55)));
        // Hauptlager Sektor 1
        adventureSteps.add(buildAttackStep(NP_1, CAMP_6, Nusala, null, 30, DONE, Rek(145)));
        adventureSteps.add(buildAttackStep(NP_1, CAMP_6, Generalmajor, "GM 2", 30, DONE, Rek(70), Sol(10), Kan(205)));
        // *** move from sector 1 to 2 ***
        // TODO Define moving steps
        //adventureSteps.add(buildMoveStep(NP_1, NP_2, Generalmajor, "GM 1", 0));
        // adventureSteps.add(buildMoveStep(NP, NP, , "", 0));
        // *** sector 2 ***
        adventureSteps.add(buildAttackStep(NP_2, CAMP_7, Generalmajor, "GM 1", 30, DONE, Rek(160), Bos(60), Kan(65)));
        adventureSteps.add(buildAttackStep(NP_2, CAMP_8, Generalmajor, "GM 1", 0, OPEN, Rek(135), Cav(30), Kan(120)));

        super.adventureSteps.clear();
        adventureSteps.forEach(item -> super.adventureSteps.add(item));
        super.saveState();
        super.restoreState();
    }

    void moveToSector2() {
        //        GM1, ### GM1 ### S1, S2, T1 ### A ### M1 + M2 ### Mary ### GM2 ###
        // moveGeneral(GeneralType.Generalmajor, MOVE_POINT_1, new Location(-50, 220));
        // moveGeneral(GeneralType.Anslem, MOVE_POINT_1, new Location(-15, 241));
        //        moveGeneral(GeneralType.MdK, MOVE_POINT_1, new Location(-10, 178));
        //        moveGeneral(GeneralType.MdK, MOVE_POINT_1, new Location(182, 158));
        //moveGeneral(GeneralType.Mary, MOVE_POINT_1.getNavigationPoint(), new Location(144, 129));
    }

}
