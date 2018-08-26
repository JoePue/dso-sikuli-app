package de.puettner.sikuli.dso.commands.ui;

import de.puettner.sikuli.dso.commands.os.WindowsPlatform;
import org.junit.Before;
import org.junit.Test;

public class IslandCommandsTest {

    private static final CommandBuilder cmdBuilder = CommandBuilder.build(new WindowsPlatform());
    private final IslandCommands islandCmds = cmdBuilder.buildIslandCommand();

    @Before
    public void before() {
        islandCmds.switchToBrowser();
    }

    @Test
    public void hightlightRegions() {
        islandCmds.hightlightRegions();
    }

}
