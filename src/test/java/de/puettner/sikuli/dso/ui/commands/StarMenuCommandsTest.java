package de.puettner.sikuli.dso.ui.commands;

import de.puettner.sikuli.dso.commands.ui.CommandBuilder;
import de.puettner.sikuli.dso.commands.ui.SikuliCommands;
import de.puettner.sikuli.dso.commands.ui.StarMenuCommands;
import org.junit.Before;
import org.junit.Test;

public class StarMenuCommandsTest {

    private final SikuliCommands sikuliCmd = CommandBuilder.build().buildIslandCommand();
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
