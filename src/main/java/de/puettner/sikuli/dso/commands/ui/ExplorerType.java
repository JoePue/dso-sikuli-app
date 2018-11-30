package de.puettner.sikuli.dso.commands.ui;

import org.sikuli.script.Pattern;

import static de.puettner.sikuli.dso.commands.ui.SikuliCommands.pattern;

public enum ExplorerType implements PatternBased {

    BraveExplorer(pattern("BraveExplorer-icon.png").similar(0.80f)),
    SuccessfulExplorer(pattern("SuccessfulExplorer-icon.png").similar(0.80f)),
    WildExplorer(pattern("WildExplorer-icon.png").similar(0.85f)),
    FearlessExplorer(pattern("FearlessExplorer-icon.png").similar(0.81f)),
    NormalExplorer(pattern("NormalExplorer-icon.png").similar(0.80f)),;

    private final Pattern pattern;

    ExplorerType(Pattern pattern) {
        this.pattern = pattern;
    }

    @Override
    public Pattern getPattern() {
        return null;
    }

}
