package de.puettner.sikuli.dso.commands.ui;

import org.sikuli.script.Pattern;

import static de.puettner.sikuli.dso.commands.ui.SikuliCommands.pattern;

public enum QuestBookMenuButtons implements MenuButton {

    GuildQuestMenuItem(pattern("GuildQuestMenuItem-icon.png").targetOffset(6, 57));

    public final Pattern pattern;

    QuestBookMenuButtons(Pattern pattern) {
        this.pattern = pattern;
    }

    public Pattern getPattern() {
        return pattern;
    }
}
