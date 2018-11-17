package de.puettner.sikuli.dso.commands.ui;

import org.sikuli.script.Pattern;

import static de.puettner.sikuli.dso.commands.ui.SikuliCommands.pattern;

public enum ExplorerAction implements MenuButton {
    TreasureSearch(pattern("TreasureFind-icon.png").targetOffset(49, -1)),
    AdventureSearch(pattern("AdventureSearch-icon.png").similar(0.90).targetOffset(4, -20));

    /** pattern of camp */
    private final Pattern pattern;

    ExplorerAction(Pattern pattern) {
        this.pattern = pattern;
    }

    @Override
    public Pattern getPattern() {
        if (pattern == null) {
            throw new IllegalStateException("Invalid Action");
        }
        return pattern;
    }
}
