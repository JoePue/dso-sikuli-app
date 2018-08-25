package de.puettner.sikuli.dso.commands.ui;

import org.sikuli.script.Region;

public class BuildMenuCommands extends MenuCommands {

    public BuildMenuCommands(Region menuRegion, IslandCommands islandCmds) {
        super(menuRegion, islandCmds);
    }

    public void clickButton(BuildMenuButtons entry) {
        islandCmds.clickIfExists(entry.pattern, menuRegion);
    }

}
