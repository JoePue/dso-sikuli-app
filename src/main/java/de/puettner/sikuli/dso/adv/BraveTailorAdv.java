package de.puettner.sikuli.dso.adv;

import de.puettner.sikuli.dso.DSOService;
import de.puettner.sikuli.dso.commands.ui.IslandCommands;
import de.puettner.sikuli.dso.commands.ui.StarMenu;
import de.puettner.sikuli.dso.commands.ui.StarMenuFilter;
import org.sikuli.script.Location;

import static de.puettner.sikuli.dso.adv.BraveTailorAttackCamp.*;

/**
 * brave tailor = tapferes Schneiderlein
 */
public class BraveTailorAdv extends Adventure {

    protected BraveTailorAdv(IslandCommands islandCmds, StarMenu starMenu, DSOService dsoService) {
        super(islandCmds, starMenu, dsoService);
    }

    public void play() {
        prepareStarMenu();
        // Sektor 1
        attack(CAMP_1, GeneralType.Vargus, Rek(120), Kan(75));
        islandCmds.sleepX(10);
        attack(CAMP_2, GeneralType.Nusala, Rek(65), Cav(100));
        islandCmds.sleepX(10);
        attack(CAMP_3, GeneralType.Generalmajor, Rek(200), Kan(85));

        // *** Verfügbarkeit abwarten ***

        islandCmds.sleepX(10);
        attackCamp4();
        islandCmds.sleepX(30);
        attackCamp5();
        // islandCmds.sleepX(30);
        attackCamp6();

        moveToSector2();
    }

    private void prepareStarMenu() {
        super.prepareStarMenu(StarMenuFilter.BraveTailor);
    }

    protected void attackCamp4() {attack(CAMP_4, GeneralType.Vargus, Rek(140), Sol(30), Kan(25));}

    protected void attackCamp5() {
        attack(CAMP_5, GeneralType.MdK, Rek(1));
        attack(CAMP_5, GeneralType.MdK, Rek(1));
        attack(CAMP_5, GeneralType.MdK, Rek(1));
        islandCmds.sleepX(30);
        attack(CAMP_5, GeneralType.Mary, Bos(160), Kan(55));
    }

    protected void attackCamp6() {
        attack(CAMP_6, GeneralType.Nusala, Rek(145));
        islandCmds.sleepX(30);
        attack(CAMP_6, GeneralType.Generalmajor, Rek(70), Sol(10), Kan(205));
    }

    void moveToSector2() {
        //        GM1, ### GM1 ### S1, S2, T1 ### A ### M1 + M2 ### Mary ### GM2 ###
        // moveGeneral(GeneralType.Generalmajor, MOVE_POINT_1, new Location(-50, 220));
        // moveGeneral(GeneralType.Anselm, MOVE_POINT_1, new Location(-15, 241));
        //        moveGeneral(GeneralType.MdK, MOVE_POINT_1, new Location(-10, 178));
        //        moveGeneral(GeneralType.MdK, MOVE_POINT_1, new Location(182, 158));
        moveGeneral(GeneralType.Mary, MOVE_POINT_1, new Location(144, 129));
    }

    protected void attackCamp7ofSector2() {
        attack(CAMP_7, GeneralType.Generalmajor, Rek(160), Bos(60), Kan(65));
    }

    protected void attackCamp8ofSector2() {
        attack(CAMP_8, GeneralType.Generalmajor, Rek(135), Cav(30), Kan(120));
    }

    protected void attackCamp9ofSector2() {

    }

    protected void attackCamp10ofSector2() {

    }

    protected void attackCamp11ofSector2() {

    }
}
