package de.puettner.sikuli.dso.commands.ui;

import lombok.extern.slf4j.Slf4j;
import org.sikuli.script.Region;

@Slf4j
public class BookbinderMenuCommands extends MenuCommands {

    public BookbinderMenuCommands(Region menuRegion, IslandCommands islandCmds) {
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
            log.error("Unknown bootType: " + bookType);
        }
        return false;
    }
}
