package de.puettner.sikuli.dso.commands.ui;

import org.junit.Before;
import org.junit.Test;

public class BuildQueueMenuCommandsTest {

    private final SikuliCommands sikuliCmd = CommandBuilder.build().buildIslandCommand();
    private final BuildQueueMenuCommands buildQueueCmds = CommandBuilder.build().buildBuildQueueMenuCommands();

    @Before
    public void before() {
        sikuliCmd.switchToBrowser();
    }

    @Test
    public void highlightMenuRegion() {
        buildQueueCmds.highlightMenuRegion();
    }

    @Test
    public void getBuildQueueSize() {
        System.out.println("BuildQueueSize: " + buildQueueCmds.getBuildQueueSize());
    }
}
