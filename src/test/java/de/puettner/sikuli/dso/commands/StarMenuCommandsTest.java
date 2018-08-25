package de.puettner.sikuli.dso.commands;

import org.junit.Before;
import org.junit.Test;

public class StarMenuCommandsTest {

    private final SikuliCommands sikuliCmd = CommandBuilder.build().buildSikuliCommand();
    private final StarMenuCommands starMenu = CommandBuilder.build().buildStarMenuCommands();

    @Before
    public void before() {
        sikuliCmd.switchToBrowser();
    }

    @Test
    public void openStarMenu() {
        starMenu.openStarMenu();
    }

}
