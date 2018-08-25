package de.puettner.sikuli.dso.commands.ui;

import org.sikuli.script.Pattern;

public enum IslandButtons implements MenuButton {
    ;
    public final Pattern pattern;

    IslandButtons(Pattern pattern) {
        this.pattern = pattern;
    }

    public Pattern getPattern() {
        return pattern;
    }
}
