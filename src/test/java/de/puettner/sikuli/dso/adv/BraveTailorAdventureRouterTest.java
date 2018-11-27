package de.puettner.sikuli.dso.adv;

import de.puettner.sikuli.dso.ui.commands.MenuTest;
import lombok.extern.java.Log;
import org.junit.Before;
import org.junit.Test;

import static de.puettner.sikuli.dso.adv.BraveTailorNavPoints.*;

@Log
public class BraveTailorAdventureRouterTest extends MenuTest {

    private BraveTailorAdventureRouter adventure = null;

    @Before
    public void before() {
        adventure = new BraveTailorAdventureRouter(islandCmds, islandCmds.getIslandRegion());
        sikuliCmd.focusBrowser();
    }

    @Test
    public void whereIam() {
        adventure.whereIam();
    }

    @Test
    public void moveToCamp() {
        adventure.moveToCamp(NP_1, NP_2, null, null);
    }

    @Test
    public void route() {
        adventure.route(NP_1, NP_4, null, null);
        adventure.route(NP_4, NP_1, null, null);
    }

    @Test
    public void centerNavigationPoint() {
        // adventure.centerNavigationPoint(NP_2, new Dimension(-55, 204), null);
        adventure.centerNavigationPoint(NP_4, null, null);
    }

}
