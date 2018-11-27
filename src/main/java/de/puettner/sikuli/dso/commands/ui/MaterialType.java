package de.puettner.sikuli.dso.commands.ui;

import org.sikuli.script.Pattern;

import static de.puettner.sikuli.dso.commands.ui.SikuliCommands.pattern;

public enum MaterialType {

    ST(null),
    MA(null),
    GR(null),
    KU(criteria(criteria(pattern("copper-source-icon.png").similar(0.90f), sectors(Sector.S2, Sector.S4)))),
    EI(criteria(criteria(pattern("iron-source-icon.png").similar(0.92f), sectors(Sector.S10, Sector.S3, Sector.S4, Sector.S5, Sector.S6,
            Sector.S8, Sector.S9)))),
    TI(null),
    KO(criteria(criteria(pattern("cole-source-icon.png").similar(0.90f), sectors(Sector.S7, Sector.S6)))),
    GO(criteria(criteria(pattern("gold-source-icon.png").similar(0.90f), sectors(Sector.S2, Sector.S9, Sector.S10)), criteria(pattern
            ("gold-source-icon-special.png").similar(0.90f), sectors(Sector.S9)))),
    SA(null);

    /* Icon für ein gefundenes Vorkommen */
    public final MaterialSector[] msl;

    MaterialType(MaterialSector[] msl) {
        this.msl = msl;
    }

    public static Sector[] sectors(Sector... sectors) {
        return sectors;
    }

    public static MaterialSector[] criteria(MaterialSector... args) {
        return args;
    }

    public static MaterialSector criteria(Pattern pattern, Sector[] sectors) {
        return MaterialSector.builder().pattern(pattern).sectors(sectors).build();
    }

}
