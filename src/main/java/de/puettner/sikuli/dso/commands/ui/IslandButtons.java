package de.puettner.sikuli.dso.commands.ui;

import org.sikuli.script.Pattern;

import static de.puettner.sikuli.dso.commands.ui.SikuliCommands.pattern;

public enum IslandButtons implements MenuButton {

    CollectableIconOne(pattern("CollectablePinkyIcon_1.png").targetOffset(0, 0)),
    CollectableIconThree(pattern("CollectablePinkyIcon_3.png").targetOffset(0, 0)),
    BookbinderBuilding(pattern("BookBinderBuilding.png").similar(0.80f));

    public final Pattern pattern;

    IslandButtons(Pattern pattern) {
        this.pattern = pattern;
    }

    public Pattern getPattern() {
        return pattern;
    }
}
