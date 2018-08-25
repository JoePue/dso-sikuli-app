package de.puettner.sikuli.dso.commands.ui;

import org.sikuli.script.Region;

public class MenuCommands {

    protected final Region menuRegion;
    protected SikuliCommands sikuliCmds;


    protected MenuCommands(Region menuRegion, SikuliCommands sikuliCmds) {
        this.menuRegion = menuRegion;
        this.sikuliCmds = sikuliCmds;
    }

    public void highlightMenuRegion() {
        this.menuRegion.highlight(2, "green");
    }
}
