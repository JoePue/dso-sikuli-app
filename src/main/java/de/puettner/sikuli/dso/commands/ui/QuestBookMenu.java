package de.puettner.sikuli.dso.commands.ui;

import org.sikuli.script.Region;

public class QuestBookMenu extends DsoMenu {

    public QuestBookMenu(Region menuRegion, IslandCommands islandCmds) {
        super(menuRegion, islandCmds);
    }

    public boolean clickButton(QuestBookMenuButtons entry) {
        return islandCmds.clickIfExists(entry.pattern, menuRegion);
    }

}
