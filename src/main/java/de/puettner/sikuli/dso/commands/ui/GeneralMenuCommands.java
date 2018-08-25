package de.puettner.sikuli.dso.commands.ui;

import lombok.extern.slf4j.Slf4j;
import org.sikuli.script.Region;

@Slf4j
public class GeneralMenuCommands {

    private Region menuRegion;
    private SikuliCommands sikuliCommands;

    public GeneralMenuCommands(Region menuRegion, SikuliCommands sikuliCommands) {
        this.menuRegion = menuRegion;
        this.sikuliCommands = sikuliCommands;
    }

    public void clickBuildMenuButton(BuildMenuButtons entry) {
        sikuliCommands.clickIfExists(entry.pattern, menuRegion);
    }
}
