package de.puettner.sikuli.dso.commands.ui;

import org.sikuli.script.Pattern;

import static de.puettner.sikuli.dso.commands.ui.SikuliCommands.pattern;

public enum ExplorerActionType implements MenuButton {
    SHORT(pattern("ExplorerActionType-Short.png").similar(0.95)),
    MEDIUM(pattern("ExplorerActionType-Medium.png").similar(0.95)),
    LONG(pattern("ExplorerActionType-Long.png").similar(0.95)),
    VERY_LONG(pattern("ExplorerActionType-VeryLong.png").similar(0.95)),
    VERY_VERY_LONG(pattern("ExplorerActionType-VeryVeryLong.png").similar(0.95)),
    ARTIFACT(null),
    RARITY(null);

    /** pattern of camp */
    private final Pattern pattern;

    ExplorerActionType(Pattern pattern) {
        this.pattern = pattern;
    }

    @Override
    public Pattern getPattern() {
        if (pattern == null) {
            throw new IllegalStateException("Invalid ExplorerActionType");
        }
        return pattern;
    }
}
