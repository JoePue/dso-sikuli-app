package de.puettner.sikuli.dso.commands.ui;

import org.sikuli.script.Region;

public class GeneralMenusCommands {

    protected final Region menuRegion;

    protected GeneralMenusCommands(Region menuRegion) {
        this.menuRegion = menuRegion;
    }

    public void highlightMenuRegion() {
        this.menuRegion.highlight(2, "green");
    }
}
