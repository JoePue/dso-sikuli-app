package de.puettner.sikuli.dso.adv;

import de.puettner.sikuli.dso.ui.commands.MenuTest;
import lombok.extern.java.Log;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;

import static de.puettner.sikuli.dso.adv.BraveTailorNavPoints.NP_1;
import static de.puettner.sikuli.dso.adv.BraveTailorNavPoints.NP_2;

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
        adventure.clickAttackCamp(BraveTailorAttackCamp.CAMP_11);
    }

    @Test
    public void moveToCamp() {
        adventure.moveToCamp(BraveTailorAttackCamp.CAMP_10, NP_2, null);
    }

    @Test
    public void route() {
        adventure.route(NP_1, NP_1, null, new Dimension(337, -116));
    }

    @Test
    public void centerNavigationPoint() {
        adventure.centerNavigationPoint(NP_1, new Dimension(-400, -100), null);
    }

}
