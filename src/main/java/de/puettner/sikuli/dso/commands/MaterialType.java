package de.puettner.sikuli.dso.commands;

import org.sikuli.script.Pattern;

import static de.puettner.sikuli.dso.commands.SikuliCommands.pattern;

public enum MaterialType {
    ST(null),
    MA(null),
    KU(pattern("copper-source-icon.png").similar(0.92f)),
    EI(pattern("iron-source-icon.png").similar(0.92f)),
    KO(null),
    GO(pattern("gold-source-icon.png").similar(0.90f)),
    GR(null),
    SA(null),
    TI(null);

    /* Icon für ein gefundenes Vorkommen */
    public final Pattern sourcePattern;

    MaterialType(Pattern sourcePattern) {
        this.sourcePattern = sourcePattern;
    }
}
