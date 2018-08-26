package de.puettner.sikuli.dso.commands.ui;

import org.sikuli.script.Pattern;

import static de.puettner.sikuli.dso.commands.ui.SikuliCommands.pattern;

public enum MaterialType {
    ST(null),
    MA(null),
    GR(null),
    KU(pattern("copper-source-icon.png").similar(0.92f)),
    EI(pattern("iron-source-icon.png").similar(0.92f)),
    TI(null),
    KO(pattern("cole-source-icon.png").similar(0.90f)),
    GO(pattern("gold-source-icon.png").similar(0.90f)),
    SA(null);

    /* Icon für ein gefundenes Vorkommen */
    public final Pattern sourcePattern;

    MaterialType(Pattern sourcePattern) {
        this.sourcePattern = sourcePattern;
    }
}
