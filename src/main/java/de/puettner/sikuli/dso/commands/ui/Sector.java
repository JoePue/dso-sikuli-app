package de.puettner.sikuli.dso.commands.ui;

public enum Sector {
    S1, S2, S3, S4, S5, S6, S7, S8, S9, S10, S11, S12;

    private static Sector[] mainSectors = new Sector[]{S1, S2, S3, S4, S5, S6, S7, S8, S9};

    public static Sector[] valuesFromS1ToS9() {
        return mainSectors;
    }

    public int index() {
        return this.ordinal() + 1;
    }
}
