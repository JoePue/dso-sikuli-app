package de.puettner.sikuli.dso.commands.ui;

import org.sikuli.script.Region;

public class MenuCommands {

    protected final Region menuRegion;
    protected IslandCommands islandCmds;

    protected MenuCommands(Region menuRegion, IslandCommands islandCmds) {
        this.menuRegion = menuRegion;
        this.islandCmds = islandCmds;
    }

    public void highlightMenuRegion() {
        this.menuRegion.highlight(2, "green");
    }

}
