package de.puettner.sikuli.dso.commands;

import lombok.extern.slf4j.Slf4j;
import org.sikuli.script.Region;

@Slf4j
public class BookbinderMenuCommands {

    private Region menuRegion;
    private SikuliCommands sikuliCommands;

    public BookbinderMenuCommands(Region appRegion, SikuliCommands sikuliCommands) {
        this.menuRegion = appRegion;
        this.sikuliCommands = sikuliCommands;
    }

    public void clickBuildMenuButton(BuildMenuButtons entry) {
        sikuliCommands.clickIfExists(entry.pattern, menuRegion);
    }

    public boolean clickOkButtonBookbinder() {
        return sikuliCommands.clickOkButton(3);
    }

    /**
     * Types: Manuskript, Kompendium, Kodex
     *
     * @param bookType
     */
    public boolean clickButton(BookbinderMenuButtons bookType) {
        if (BookbinderMenuButtons.Manusskript.equals(bookType)) {
            return sikuliCommands.click(BookbinderMenuButtons.Manusskript.pattern);
        } else if (BookbinderMenuButtons.Kompendium.equals(bookType)) {
            return sikuliCommands.click(BookbinderMenuButtons.Kompendium.pattern);
        } else if (BookbinderMenuButtons.Kodex.equals(bookType)) {
            return sikuliCommands.click(BookbinderMenuButtons.Kodex.equals(bookType));
        } else {
            log.error("Unknown bootType: " + bookType);
        }
        return false;
    }
}
