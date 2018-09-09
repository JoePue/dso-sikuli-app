package de.puettner.sikuli.dso.adv;

import de.puettner.sikuli.dso.ui.commands.MenuTest;
import org.junit.Before;
import org.junit.Test;

public class BraveTailorAdvTest extends MenuTest {

    private final BraveTailorAdv adventure = AdventureBuilder.build().buildTapferesSchneiderleinAT();

    @Before
    public void before() {
        sikuliCmd.focusBrowser();
    }

    @Test
    public void play() {
        adventure.play();
    }

    @Test
    public void attack() {
        adventure.attack(BraveTailorAttackCamp.CAMP_4);
    }

    @Test
    public void campDragNDrop() {
        BraveTailorAttackCamp camp = BraveTailorAttackCamp.CAMP_2;
        islandCmds.dragDrop(camp.getDragNDrop().x, camp.getDragNDrop().y);
    }
}
