package de.puettner.sikuli.dso.commands.ui;

import org.sikuli.script.Pattern;

public enum BookbinderMenuButtons implements MenuButton {
    Manusskript(SikuliCommands.pattern("Manusskript-Button.png").similar(0.93f)),
    Kompendium(SikuliCommands.pattern("Kompendium-Button.png").similar(0.93f)),
    Kodex(SikuliCommands.pattern("Kodex-Button.png").similar(0.93f));

    public final Pattern pattern;

    BookbinderMenuButtons(Pattern pattern) {
        this.pattern = pattern;
    }

    public Pattern getPattern() {
        return pattern;
    }
}
