package de.puettner.sikuli.dso.commands;

import org.sikuli.script.Pattern;

import static de.puettner.sikuli.dso.commands.SikuliCommands.pattern;

public enum BuildMenuButtons {
    // *************** Building-Buttons ******************
    /** Verbesserter Gebäude */
    ImprovedBuildingButton(pattern("ImprovedBuildingsButton.png").similar(0.90f)),
    // gehobene Gebäude
    RaisedBuildingButton(pattern("RaisedBuildingsButton.png").similar(0.90f)),

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
}
