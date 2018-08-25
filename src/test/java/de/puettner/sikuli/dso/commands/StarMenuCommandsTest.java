package de.puettner.sikuli.dso.commands;

import org.junit.Before;
import org.junit.Test;

public class StarMenuCommandsTest {

    private final SikuliCommands sikuliCmd = CommandBuilder.buildSikuliCommand();
    private final StarMenuCommands starMenu = CommandBuilder.buildStarMenuCommands();

    @Before
    public void before() {
        sikuliCmd.switchToBrowser();
    }

    @Test
    public void openStarMenu() {
        starMenu.openStarMenu();
    }

}
