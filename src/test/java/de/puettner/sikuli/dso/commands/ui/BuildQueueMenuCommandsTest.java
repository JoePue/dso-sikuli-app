package de.puettner.sikuli.dso.commands.ui;

import org.junit.Before;
import org.junit.Test;

public class BuildQueueMenuCommandsTest {

    private final SikuliCommands sikuliCmd = CommandBuilder.build().buildIslandCommand();
    private final BuildQueueMenuCommands buildQueueMenu = CommandBuilder.build().buildBuildQueueMenuCommands();

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
