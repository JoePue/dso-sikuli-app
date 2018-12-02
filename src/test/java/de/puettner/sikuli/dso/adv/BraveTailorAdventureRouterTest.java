package de.puettner.sikuli.dso.adv;

import de.puettner.sikuli.dso.ui.commands.MenuTest;
import lombok.extern.java.Log;
import org.junit.Before;
import org.junit.Test;
import org.sikuli.script.App;
import org.sikuli.script.Location;

import static de.puettner.sikuli.dso.adv.BraveTailorNavPoints.*;
import static de.puettner.sikuli.dso.commands.os.WindowsPlatform.CHROME_EXE;

@Log
public class BraveTailorAdventureRouterTest extends MenuTest {

    private BraveTailorAdventureRouter adventure = null;

    @Before
    public void before() {
        App app = new App(CHROME_EXE);
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
        adventure.centerNavigationPoint(NP_1);
        log.info("=============================================");
        adventure.route(NP_1, NP_4, null, null, null);
        // adventure.route(NP_4, NP_1, null, null, null);
    }

    @Test
    public void centerNavigationPoint() {
        adventure.centerNavigationPoint(NP_4);
    }

    @Test
    public void getMidpoint() {
        adventure.highlightRegion();
        Location location = adventure.getMidpoint();
        log.info("location " + location);
    }

}
