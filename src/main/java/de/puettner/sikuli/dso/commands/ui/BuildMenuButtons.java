package de.puettner.sikuli.dso.commands.ui;

import org.sikuli.script.Pattern;

public enum BuildMenuButtons implements MenuButton {
    // *************** Building-Buttons ******************
    /** Verbesserter Gebäude */
    ImprovedBuildingButton(SikuliCommands.pattern("ImprovedBuildingsButton.png").similar(0.90f)),
    // gehobene Gebäude
    RaisedBuildingButton(SikuliCommands.pattern("RaisedBuildingsButton.png").similar(0.90f)),

    // Building dependent buttons
    CopperMineButton(SikuliCommands.pattern("CopperMineBuildingButton").similar(0.90f)),
    IronMineButton(SikuliCommands.pattern("IronMineBuildingButton").similar(0.90f)),
    ColeMineButton(SikuliCommands.pattern("ColeMineBuildingButton").similar(0.90f)),
    GoldMineButton(SikuliCommands.pattern("GoldMineBuildingButton").similar(0.90f)),
    ;
    public final Pattern pattern;

    BuildMenuButtons(Pattern pattern) {
        this.pattern = pattern;
    }

    public Pattern getPattern() {
        return pattern;
    }
}
