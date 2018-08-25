package de.puettner.sikuli.dso.commands;

import org.sikuli.script.Match;
import org.sikuli.script.Region;

import java.util.Iterator;

import static de.puettner.sikuli.dso.commands.SikuliCommands.pattern;

public class BuildMenuCommands {

    private Region menuRegion;
    private SikuliCommands sikuliCommands;

    public BuildMenuCommands(Region menuRegion, SikuliCommands sikuliCommands) {
        this.menuRegion = menuRegion;
        this.sikuliCommands = sikuliCommands;
    }

    public void clickButton(BuildMenuButtons entry) {
        sikuliCommands.clickIfExists(entry.pattern, menuRegion);
    }

    public void buildMine(String s) {

    }

}
