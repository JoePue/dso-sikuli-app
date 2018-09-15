package de.puettner.sikuli.dso.adv;

import de.puettner.sikuli.dso.ui.commands.MenuTest;
import lombok.extern.java.Log;
import org.junit.Before;
import org.junit.Test;

@Log
public class BraveTailorAdvTest extends MenuTest {

    private final BraveTailorAdv adventure = AdventureBuilder.build().buildBraveTailorAdv();

    @Before
    public void before() {
        sikuliCmd.focusBrowser();
    }

    @Test
    public void play() {
        adventure.saveBraveTailorInitState();
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
    public void saveBraveTailorInitState() {
        adventure.saveBraveTailorInitState();
    }

    @Test
    public void hoverRegionCenter() {
        adventure.hoverRegionCenter();
    }

    @Test
    public void whereIam() {
        adventure.whereIam();
    }

}
