package de.puettner.sikuli.dso.commands;

import lombok.extern.slf4j.Slf4j;
import org.sikuli.script.Region;

@Slf4j
public class TempCommands {

    private Region menuRegion;
    private SikuliCommands sikuliCommands;

    public TempCommands(Region menuRegion, SikuliCommands sikuliCommands) {
        this.menuRegion = menuRegion;
        this.sikuliCommands = sikuliCommands;
    }

    public void clickBuildMenuButton(BuildMenuButtons entry) {
        sikuliCommands.clickIfExists(entry.pattern, menuRegion);
    }
}
