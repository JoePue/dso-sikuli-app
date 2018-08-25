package de.puettner.sikuli.dso.commands.ui;

import org.sikuli.script.Region;

public class MenuCommands {

    protected final Region menuRegion;
    protected SikuliCommands sikuliCommands;


    protected MenuCommands(Region menuRegion, SikuliCommands sikuliCommands) {
        this.menuRegion = menuRegion;
        this.sikuliCommands = sikuliCommands;
    }

    public void highlightMenuRegion() {
        this.menuRegion.highlight(2, "green");
    }
}
