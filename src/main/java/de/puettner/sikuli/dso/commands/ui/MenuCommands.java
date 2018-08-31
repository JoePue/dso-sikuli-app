package de.puettner.sikuli.dso.commands.ui;

import org.sikuli.script.Region;

import static de.puettner.sikuli.dso.commands.ui.SikuliCommands.initRegion;

public class MenuCommands {

    protected final Region menuRegion;
    protected IslandCommands islandCmds;

    protected MenuCommands(Region menuRegion, IslandCommands islandCmds) {
        this.menuRegion = menuRegion;
        this.islandCmds = islandCmds;
        initRegion(menuRegion);
    }

    public void highlightMenuRegion() {
        this.menuRegion.highlight(2, "green");
    }

}
