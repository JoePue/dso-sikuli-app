package de.puettner.sikuli.dso.commands.ui;

import org.sikuli.script.Pattern;

import static de.puettner.sikuli.dso.commands.ui.SikuliCommands.pattern;

public enum ExplorerType implements PatternBased {
    /** Mutige Entdeckerin */
    BraveExplorer(pattern("BraveExplorer-icon.png").similar(0.80f)),
    /** Erfolgreicher Entdecker */
    SuccessfulExplorer(pattern("SuccessfulExplorer-icon.png").similar(0.80f)),
    /** Wilder Kundschafter */
    WildExplorer(pattern("WildExplorer-icon.png").similar(0.85f)),
    /** Unerschrokene Entdeckerin */
    FearlessExplorer(pattern("FearlessExplorer-icon.png").similar(0.81f)),
    /** normaler Entdecker */
    NormalExplorer(pattern("NormalExplorer-icon.png").similar(0.80f));

    private final Pattern pattern;

    ExplorerType(Pattern pattern) {
        this.pattern = pattern;
    }

    @Override
    public Pattern getPattern() {
        return pattern;
    }
}
