package de.puettner.sikuli.dso.ui.commands;

import de.puettner.sikuli.dso.commands.ui.IslandCommands;
import de.puettner.sikuli.dso.commands.ui.MenuBuilder;
import de.puettner.sikuli.dso.commands.ui.SikuliCommands;
import org.junit.Before;

public class MenuTest {

    protected final SikuliCommands sikuliCmd = MenuBuilder.build().buildIslandCommand();
    protected final IslandCommands islandCmds = MenuBuilder.build().buildIslandCommand();

    @Before
    public void before() {
        islandCmds.focusBrowser();
    }
}
