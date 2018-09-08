package de.puettner.sikuli.dso.commands.ui;

import org.sikuli.script.Pattern;

import static de.puettner.sikuli.dso.commands.ui.SikuliCommands.pattern;

public enum OkButton implements MenuButton {

    SMALL_OK(pattern("Ok-Button-0.png")),
    BIG_OK(pattern("Ok-Button-1.png")),
    LOGIN_OK(pattern("Ok-Button-2.png")),
    BOOKBINDER_OK(pattern("Ok-Button-3-Bookbinder.png")),;
    public final Pattern pattern;

    OkButton(Pattern pattern) {
        this.pattern = pattern;
    }

    public Pattern getPattern() {
        return pattern;
    }
}
