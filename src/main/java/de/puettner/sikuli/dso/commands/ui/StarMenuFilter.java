package de.puettner.sikuli.dso.commands.ui;

public enum StarMenuFilter {

    ENTDDECK_KUNDSCH_GEOLO("entd|kunds|geolo|steak|irma"),
    EIGTH_PERCENT("8%");

    public final String filterString;

    StarMenuFilter(String filterString) {
        this.filterString = filterString;
    }
}