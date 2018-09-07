package de.puettner.sikuli.dso.commands.ui;

import org.sikuli.script.Pattern;

import static de.puettner.sikuli.dso.commands.ui.BuildMenuTabs.ImprovedBuildingButton;
import static de.puettner.sikuli.dso.commands.ui.BuildMenuTabs.RaisedBuildingButton;

public enum BuildMenuButtons implements MenuButton {

    // Building dependent buttons
    CopperMineButton(SikuliCommands.pattern("CopperMineBuildingButton").similar(0.90f), ImprovedBuildingButton),
    IronMineButton(SikuliCommands.pattern("IronMineBuildingButton").similar(0.92f), RaisedBuildingButton),
    ColeMineButton(SikuliCommands.pattern("ColeMineBuildingButton").similar(0.92f), RaisedBuildingButton),
    GoldMineButton(SikuliCommands.pattern("GoldMineBuildingButton").similar(0.92f), RaisedBuildingButton);

    public final Pattern pattern;
    /** this button is only available in this tab */
    public final BuildMenuTabs tab;

    BuildMenuButtons(Pattern pattern, BuildMenuTabs tab) {
        this.pattern = pattern;
        this.tab = tab;
    }

    public Pattern getPattern() {
        return pattern;
    }

}
