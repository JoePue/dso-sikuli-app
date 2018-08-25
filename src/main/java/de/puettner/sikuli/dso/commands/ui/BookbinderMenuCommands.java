package de.puettner.sikuli.dso.commands.ui;

import lombok.extern.slf4j.Slf4j;
import org.sikuli.script.Region;

@Slf4j
public class BookbinderMenuCommands extends MenuCommands {

    public BookbinderMenuCommands(Region menuRegion, SikuliCommands sikuliCmds) {
        super(menuRegion, sikuliCmds);
    }

    public void clickBuildMenuButton(BuildMenuButtons entry) {
        sikuliCmds.clickIfExists(entry.pattern, menuRegion);
    }

    public boolean clickOkButtonBookbinder() {
        return sikuliCmds.clickOkButton(3);
    }

    /**
     * Types: Manuskript, Kompendium, Kodex
     *
     * @param bookType
     */
    public boolean clickButton(BookbinderMenuButtons bookType) {
        if (BookbinderMenuButtons.Manusskript.equals(bookType)) {
            return sikuliCmds.click(BookbinderMenuButtons.Manusskript.pattern);
        } else if (BookbinderMenuButtons.Kompendium.equals(bookType)) {
            return sikuliCmds.click(BookbinderMenuButtons.Kompendium.pattern);
        } else if (BookbinderMenuButtons.Kodex.equals(bookType)) {
            return sikuliCmds.click(BookbinderMenuButtons.Kodex.equals(bookType));
        } else {
            log.error("Unknown bootType: " + bookType);
        }
        return false;
    }
}
