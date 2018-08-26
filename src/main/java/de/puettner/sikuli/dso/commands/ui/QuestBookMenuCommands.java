package de.puettner.sikuli.dso.commands.ui;

import org.sikuli.script.Region;

public class QuestBookMenuCommands extends MenuCommands {

    public QuestBookMenuCommands(Region menuRegion, IslandCommands islandCmds) {
        super(menuRegion, islandCmds);
    }

    public void clickButton(QuestBookMenuButtons entry) {
        islandCmds.clickIfExists(entry.pattern, menuRegion);
    }

}