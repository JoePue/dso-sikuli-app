package de.puettner.sikuli.dso.commands.ui;

import org.sikuli.script.Pattern;

import static de.puettner.sikuli.dso.commands.ui.SikuliCommands.pattern;

/**
 * Building Tabs-Buttons
 */
public enum BuildMenuTabs implements MenuButton {
    /** Verbesserter Gebäude */
    ImprovedBuildingButton(pattern("ImprovedBuildingsButton.png").similar(0.90f)),
    // gehobene Gebäude
    RaisedBuildingButton(pattern("RaisedBuildingsButton.png").similar(0.90f)),
    RaisedBuildingMenuDisabled(pattern("RaisedBuildingsButtonsdisabled.png").similar(0.98f));

    public final Pattern pattern;

    BuildMenuTabs(Pattern pattern) {
        this.pattern = pattern;
    }

    public Pattern getPattern() {
        return pattern;
    }
}
