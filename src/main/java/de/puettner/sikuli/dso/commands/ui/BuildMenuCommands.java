package de.puettner.sikuli.dso.commands.ui;

import org.sikuli.script.Region;

public class BuildMenuCommands extends MenuCommands {

    public BuildMenuCommands(Region menuRegion, SikuliCommands sikuliCmds) {
        super(menuRegion, sikuliCmds);
    }

    public void clickButton(BuildMenuButtons entry) {
        sikuliCmds.clickIfExists(entry.pattern, menuRegion);
    }

    public void buildMine(String s) {

    }

}
