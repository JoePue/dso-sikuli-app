package de.puettner.sikuli.dso.ui.commands;

import de.puettner.sikuli.dso.commands.ui.MenuBuilder;
import de.puettner.sikuli.dso.commands.ui.StarMenu;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

public class StarMenuTest extends MenuTest {

    private final StarMenu starMenu = MenuBuilder.build().buildStarMenuCommands();

    @Before
    public void before() {
        sikuliCmd.focusBrowser();
    }

    @Test
    public void openStarMenu() {
        starMenu.openStarMenu(Optional.empty());
    }

    @Test
    public void openMessageBox() {
        starMenu.openMessageBox();
    }

}
