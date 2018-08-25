package de.puettner.sikuli.dso.commands.ui;

import org.sikuli.script.Pattern;

public enum MaterialType {
    ST(null),
    MA(null),
    KU(SikuliCommands.pattern("copper-source-icon.png").similar(0.92f)),
    EI(SikuliCommands.pattern("iron-source-icon.png").similar(0.92f)),
    KO(null),
    GO(SikuliCommands.pattern("gold-source-icon.png").similar(0.90f)),
    GR(null),
    SA(null),
    TI(null);

    /* Icon für ein gefundenes Vorkommen */
    public final Pattern sourcePattern;

    MaterialType(Pattern sourcePattern) {
        this.sourcePattern = sourcePattern;
    }
}
