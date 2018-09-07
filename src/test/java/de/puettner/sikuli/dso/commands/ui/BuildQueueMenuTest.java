package de.puettner.sikuli.dso.commands.ui;

import org.junit.Before;
import org.junit.Test;

public class BuildQueueMenuTest {

    private final SikuliCommands sikuliCmd = MenuBuilder.build().buildIslandCommand();
    private final BuildQueueMenu buildQueueMenu = MenuBuilder.build().buildBuildQueueMenuCommands();

    @Before
    public void before() {
        sikuliCmd.focusBrowser();
    }

    @Test
    public void highlightMenuRegion() {
        buildQueueMenu.highlightMenuRegion();
    }

    @Test
    public void getBuildQueueSize() {
        System.out.println("BuildQueueSize: " + buildQueueMenu.getBuildQueueSize());
    }
}
