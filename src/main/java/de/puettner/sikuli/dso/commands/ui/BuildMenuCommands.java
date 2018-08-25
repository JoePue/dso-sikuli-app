package de.puettner.sikuli.dso.commands.ui;

import org.sikuli.script.Region;

public class BuildMenuCommands extends MenuCommands {

    public BuildMenuCommands(Region menuRegion, SikuliCommands sikuliCommands) {
        super(menuRegion, sikuliCommands);
    }

    public void clickButton(BuildMenuButtons entry) {
        sikuliCommands.clickIfExists(entry.pattern, menuRegion);
    }

    public void buildMine(String s) {

    }

}
