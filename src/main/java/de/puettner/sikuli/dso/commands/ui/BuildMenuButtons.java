package de.puettner.sikuli.dso.commands.ui;

import org.sikuli.script.Pattern;

import static de.puettner.sikuli.dso.commands.ui.SikuliCommands.pattern;

public enum BuildMenuButtons implements MenuButton {
    // *************** Building-Buttons ******************
    /** Verbesserter Gebäude */
    ImprovedBuildingButton(pattern("ImprovedBuildingsButton.png").similar(0.90f)),
    // gehobene Gebäude
    RaisedBuildingButton(pattern("RaisedBuildingsButton.png").similar(0.90f)),

    RaisedBuildingMenuDisabled(pattern("RaisedBuildingsButtonsdisabled.png").similar(0.98f)),

    // Building dependent buttons
    CopperMineButton(SikuliCommands.pattern("CopperMineBuildingButton").similar(0.90f)),
    IronMineButton(SikuliCommands.pattern("IronMineBuildingButton").similar(0.92f)),
    ColeMineButton(SikuliCommands.pattern("ColeMineBuildingButton").similar(0.92f)),
    GoldMineButton(SikuliCommands.pattern("GoldMineBuildingButton").similar(0.92f)),
    ;
    public final Pattern pattern;

    BuildMenuButtons(Pattern pattern) {
        this.pattern = pattern;
    }

    public Pattern getPattern() {
        return pattern;
    }
}
