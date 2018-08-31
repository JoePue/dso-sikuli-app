package de.puettner.sikuli.dso.commands.ui;

import org.junit.Before;
import org.junit.Test;

public class QuestBookMenuCommandsTest {

    private static final CommandBuilder cmdBuilder = CommandBuilder.build();
    private final IslandCommands islandCmds = cmdBuilder.buildIslandCommand();
    private final QuestBookMenuCommands menuCmds = cmdBuilder.buildQuestBookMenuCommands();

    @Before
    public void before() {
        islandCmds.focusBrowser();
    }

    @Test
    public void highlightMenuRegion() {
        menuCmds.highlightMenuRegion();
    }

}
