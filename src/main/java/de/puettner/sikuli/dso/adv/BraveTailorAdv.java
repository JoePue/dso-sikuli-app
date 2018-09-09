package de.puettner.sikuli.dso.adv;

import de.puettner.sikuli.dso.commands.ui.IslandCommands;
import de.puettner.sikuli.dso.commands.ui.StarMenu;

import static de.puettner.sikuli.dso.adv.BraveTailorAttackCamp.*;

/**
 * brave tailor = tapferes Schneiderlein
 */
public class BraveTailorAdv extends Adventure {

    protected BraveTailorAdv(IslandCommands islandCmds, StarMenu starMenu) {
        super(islandCmds, starMenu);
    }

    public void play() {
        // Sektor 1
        attack(CAMP_1, GeneralType.Vargus, Rek(120), Kan(75));
        islandCmds.sleepX(10);
        attack(CAMP_2, GeneralType.Nusala, Rek(65), Cav(100));
        islandCmds.sleepX(10);
        attack(CAMP_3, GeneralType.Generalmajor, Rek(200), Kan(85));

        // *** Verfügbarkeit abwarten ***

        islandCmds.sleepX(10);
        attack(CAMP_4, GeneralType.Vargus, Rek(140), Sol(30), Kan(25));
        islandCmds.sleepX(60);
        attack(CAMP_5, GeneralType.MdK, Rek(1));
        attack(CAMP_5, GeneralType.MdK, Rek(1));
        attack(CAMP_5, GeneralType.MdK, Rek(1));
        islandCmds.sleepX(60);
        attack(CAMP_5, GeneralType.Mary, Bos(160), Kan(55));
        islandCmds.sleepX(60);
    }

}
