package de.puettner.sikuli.dso.commands.ui;

import org.sikuli.script.Pattern;

import static de.puettner.sikuli.dso.commands.ui.SikuliCommands.pattern;

public enum MessageBoxButton implements MenuButton {

    OK_BUILDING(pattern("Ok-Building-Button.png").similar(0.90)),
    OK_STAR(pattern("Ok-Star-Button.png").similar(0.90)),
    ExplorerTreasureRewardMessageIcon(pattern("Message-Box-Source-Icon.png").similar(0.90)),
    ExplorerCardFragmentsRewardMessageIcon(pattern("Message-Box-CardFragments-Icon.png").similar(0.90));
    public final Pattern pattern;

    /** this button is only available in this tab */

    MessageBoxButton(Pattern pattern) {
        this.pattern = pattern;
    }

    public Pattern getPattern() {
        return pattern;
    }
}
