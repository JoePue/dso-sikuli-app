package de.puettner.sikuli.dso.commands.ui;

import org.junit.Before;
import org.junit.Test;

public class QuestBookMenuTest {

    private static final MenuBuilder menuBuilder = MenuBuilder.build();
    private final IslandCommands islandCmds = menuBuilder.buildIslandCommand();
    private final QuestBookMenu menuCmds = menuBuilder.buildQuestBookMenuCommands();

    @Before
    public void before() {
        islandCmds.focusBrowser();
    }

    @Test
    public void highlightMenuRegion() {
        menuCmds.highlightMenuRegion();
    }

}
