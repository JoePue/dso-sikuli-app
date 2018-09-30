package de.puettner.sikuli.dso.ui.commands;

import de.puettner.sikuli.dso.commands.ui.MenuBuilder;
import de.puettner.sikuli.dso.commands.ui.StarMenu;
import de.puettner.sikuli.dso.commands.ui.StarMenuFilter;
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
        // starMenu.openStarMenu(Optional.empty());
        starMenu.openStarMenu(Optional.of(StarMenuFilter.EIGTH_PERCENT));
    }

    @Test
    public void openMessageBox() {
        starMenu.openMessageBox();
    }

}
