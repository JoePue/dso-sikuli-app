package de.puettner.sikuli.dso.commands.ui;

import org.junit.Before;
import org.junit.Test;

public class IslandCommandsTest {

    private static final MenuBuilder menuBuilder = MenuBuilder.build();
    private final IslandCommands islandCmds = menuBuilder.buildIslandCommand();

    @Before
    public void before() {
        islandCmds.focusBrowser();
    }

    @Test
    public void hightlightRegions() {
        islandCmds.hightlightRegions();
    }

    @Test
    public void parkMouse() {
        islandCmds.parkMouse();
    }

    @Test
    public void dragDrop() {
        islandCmds.dragDrop(200, 750);
    }


}
