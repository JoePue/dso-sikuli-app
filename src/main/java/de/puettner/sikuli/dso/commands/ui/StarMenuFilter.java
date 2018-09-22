package de.puettner.sikuli.dso.commands.ui;

public enum StarMenuFilter {

    ENTDDECK_KUNDSCH_GEOLO("entd|kunds|geolo|steak|irma"),
    EIGTH_PERCENT("8%"),
    GeneralsFilterString("GM 1|Var|Ans|Nus|MdK|Mar|Vet");

    public final String filterString;

    StarMenuFilter(String filterString) {
        this.filterString = filterString;
    }
}
