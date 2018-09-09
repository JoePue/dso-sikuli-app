package de.puettner.sikuli.dso.commands.ui;

import org.sikuli.script.Pattern;
import org.sikuli.script.Region;

import java.util.function.BooleanSupplier;

import static de.puettner.sikuli.dso.commands.ui.SikuliCommands.initRegion;

public class DsoMenu {

    protected final Region menuRegion;
    protected IslandCommands islandCmds;

    protected DsoMenu(Region menuRegion, IslandCommands islandCmds) {
        this.menuRegion = menuRegion;
        this.islandCmds = islandCmds;
        initRegion(menuRegion);
    }

    public void highlightMenuRegion() {
        this.menuRegion.highlight(2, "green");
    }

    public int hover() {
        return this.menuRegion.hover();
    }

    public Region getMenuRegion() {
        return menuRegion;
    }

    protected boolean clickIfExists(MenuButton menuButton) {
        return islandCmds.clickIfExists(menuButton, menuRegion);
    }

    protected boolean clickIfExists(Pattern pattern) {
        return islandCmds.clickIfExists(pattern, menuRegion);
    }

    protected boolean waitUntilExists(BooleanSupplier supplier) {
        return islandCmds.waitUntilExists(supplier);
    }
}
