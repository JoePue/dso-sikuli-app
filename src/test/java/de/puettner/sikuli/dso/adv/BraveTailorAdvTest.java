package de.puettner.sikuli.dso.adv;

import de.puettner.sikuli.dso.ui.commands.MenuTest;
import lombok.extern.java.Log;
import org.junit.Before;
import org.junit.Test;

import static de.puettner.sikuli.dso.adv.BraveTailorNavPoints.*;

@Log
public class BraveTailorAdvTest extends MenuTest {

    private final BraveTailorAdv adventure = AdventureBuilder.build().buildBraveTailorAdv();

    @Before
    public void before() {
        sikuliCmd.focusBrowser();
    }

    @Test
    public void play() {
        // adventure.saveBraveTailorInitState(); // NO NO NO !!!
        adventure.play();
    }


    @Test
    public void saveState() {
        adventure.saveState();
    }

    @Test
    public void restoreState() {
        adventure.restoreState();
    }

    @Test
    public void hoverRegionCenter() {
        adventure.hoverRegionCenter();
    }

    @Test
    public void whereIam() {
        adventure.whereIam();
    }

    @Test
    public void clickAttackCamp() {
        adventure.clickAttackCamp(BraveTailorAttackCamp.CAMP_4);
    }

    @Test
    public void moveToCamp() {
        adventure.moveToCamp(NP_1, NP_2, null);
    }

    @Test
    public void route() {
        adventure.route(NP_1, NP_4, null, null);
        adventure.route(NP_4, NP_1, null, null);
        //        adventure.route(NP_2, NP_4, null, null);
        //        adventure.route(NP_4, NP_2, null, null);
        //        adventure.route(NP_3, NP_4, null, null);
        //        adventure.route(NP_4, NP_3, null, null);
        // adventure.route(NP_1, NP_1, null, new Dimension(337, -116));
        // adventure.route(NP_1, NP_2, null, null);
        // adventure.route(NP_2, NP_1, null, null);
        //adventure.route(NP_1, NP_3, null, null);
        //adventure.route(NP_3, NP_1, null, null);
    }

    @Test
    public void centerNavigationPoint() {
        // adventure.centerNavigationPoint(NP_2, new Dimension(-55, 204), null);
        adventure.centerNavigationPoint(NP_4, null, null);
    }

}
