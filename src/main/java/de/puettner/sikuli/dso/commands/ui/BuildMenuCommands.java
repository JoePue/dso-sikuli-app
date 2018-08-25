package de.puettner.sikuli.dso.commands.ui;

import org.sikuli.script.Region;

public class BuildMenuCommands extends GeneralMenusCommands {

    private Region menuRegion;
    private SikuliCommands sikuliCommands;

    public BuildMenuCommands(Region menuRegion, SikuliCommands sikuliCommands) {
        super(menuRegion);
        this.sikuliCommands = sikuliCommands;
    }

    public void clickButton(BuildMenuButtons entry) {
        sikuliCommands.clickIfExists(entry.pattern, menuRegion);
    }

    public void buildMine(String s) {

    }

}
