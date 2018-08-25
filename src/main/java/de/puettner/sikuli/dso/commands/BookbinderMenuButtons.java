package de.puettner.sikuli.dso.commands;

import org.sikuli.script.Pattern;

import static de.puettner.sikuli.dso.commands.SikuliCommands.pattern;

public enum BookbinderMenuButtons {
    Manusskript(pattern("Manusskript-Button.png").similar(0.93f)),
    Kompendium(pattern("Kompendium-Button.png").similar(0.93f)),
    Kodex(pattern("Kodex-Button.png").similar(0.93f));

    public final Pattern pattern;

    BookbinderMenuButtons(Pattern pattern) {
        this.pattern = pattern;
    }
}
