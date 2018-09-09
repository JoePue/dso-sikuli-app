package de.puettner.sikuli.dso.adv;

import de.puettner.sikuli.dso.ui.commands.MenuTest;
import org.junit.Test;

/**
 * Created by joerg.puettner on 08.09.2018.
 */
public class BraveTailorAttackCampTest extends MenuTest {

    @Test
    public void campClickTest() {
        islandCmds.clickIfExists(BraveTailorAttackCamp.CAMP_3.getPattern(), islandCmds.getIslandRegion());

    }
}
