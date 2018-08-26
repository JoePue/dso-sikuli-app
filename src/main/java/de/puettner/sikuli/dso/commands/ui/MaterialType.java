package de.puettner.sikuli.dso.commands.ui;

import org.sikuli.script.Pattern;

import static de.puettner.sikuli.dso.commands.ui.SikuliCommands.pattern;

public enum MaterialType {

    ST(null, null),
    MA(null, null),
    GR(null, null),
    KU(pattern("copper-source-icon.png").similar(0.92f), sectors(Sector.S2, Sector.S4)),
    EI(pattern("iron-source-icon.png").similar(0.92f), sectors(Sector.S10, Sector.S3, Sector.S4, Sector.S5, Sector.S6, Sector.S8, Sector
            .S9)),
    TI(null, null),
    KO(pattern("cole-source-icon.png").similar(0.90f), sectors(Sector.S7, Sector.S6)),
    GO(pattern("gold-source-icon.png").similar(0.90f), sectors(Sector.S2, Sector.S9, Sector.S10), pattern("gold-source-icon-special" + "" +
            ".png").similar(0.90f), sectors(Sector.S9)),
    SA(null, null);

    /* Icon für ein gefundenes Vorkommen */
    public final Pattern sourcePattern;
    public final Sector[] sourceSectors;
    public final Pattern additionalSourcePattern;
    public final Sector[] additionalSourceSectors;

    MaterialType(Pattern sourcePattern, Sector[] sourceSectors) {
        this(sourcePattern, sourceSectors, null, null);
    }

    MaterialType(Pattern sourcePattern, Sector[] sourceSectors, Pattern additionalSourcePattern, Sector[] additionalSourceSectors) {
        this.sourcePattern = sourcePattern;
        this.sourceSectors = sourceSectors;
        this.additionalSourcePattern = additionalSourcePattern;
        this.additionalSourceSectors = additionalSourceSectors;
    }

    public static Sector[] sectors(Sector... sectors) {
        return sectors;
    }
}
