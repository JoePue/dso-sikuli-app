package de.puettner.sikuli.dso.commands.ui;

import lombok.extern.java.Log;
import org.sikuli.script.Region;

import java.util.logging.Level;

@Log
public class BookbinderMenu extends DsoMenu {

    public BookbinderMenu(Region menuRegion, IslandCommands islandCmds) {
        super(menuRegion, islandCmds);
    }

    public void clickBuildMenuButton(BuildMenuButtons entry) {
        islandCmds.clickIfExists(entry.pattern, menuRegion);
    }

    public boolean clickOkButtonBookbinder() {
        return islandCmds.clickOkButton(3);
    }

    /**
     * Types: Manuskript, Kompendium, Kodex
     *
     * @param bookType
     */
    public boolean clickButton(BookbinderMenuButtons bookType) {
        if (BookbinderMenuButtons.Manusskript.equals(bookType)) {
            return islandCmds.click(BookbinderMenuButtons.Manusskript.pattern);
        } else if (BookbinderMenuButtons.Kompendium.equals(bookType)) {
            return islandCmds.click(BookbinderMenuButtons.Kompendium.pattern);
        } else if (BookbinderMenuButtons.Kodex.equals(bookType)) {
            return islandCmds.click(BookbinderMenuButtons.Kodex.pattern);
        } else {
            log.log(Level.SEVERE, "Unknown bootType: " + bookType);
        }
        return false;
    }
}
