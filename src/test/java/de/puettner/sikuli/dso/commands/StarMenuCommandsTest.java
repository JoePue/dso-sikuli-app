package de.puettner.sikuli.dso.commands;

import org.junit.Before;
import org.junit.Test;

public class StarMenuCommandsTest {

    private final SikuliCommands sikuliCmd = SikuliCommands.build();
    private StarMenuCommands starMenu;

    @Before
    public void before() {
        starMenu = sikuliCmd.buildStarMenuCommands();
        sikuliCmd.switchToBrowser();
    }

    @Test
    public void openStarMenu() {
        starMenu.openStarMenu();
    }

}
