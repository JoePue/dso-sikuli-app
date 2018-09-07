package de.puettner.sikuli.dso.ui.commands;

import de.puettner.sikuli.dso.commands.ui.MenuBuilder;
import de.puettner.sikuli.dso.commands.ui.SikuliCommands;
import de.puettner.sikuli.dso.commands.ui.StarDsoMenu;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

public class StarMenuTest {

    private final SikuliCommands sikuliCmd = MenuBuilder.build().buildIslandCommand();
    private final StarDsoMenu starMenu = MenuBuilder.build().buildStarMenuCommands();

    @Before
    public void before() {
        sikuliCmd.focusBrowser();
    }

    @Test
    public void openStarMenu() {
        starMenu.openStarMenu(Optional.empty());
    }

}
