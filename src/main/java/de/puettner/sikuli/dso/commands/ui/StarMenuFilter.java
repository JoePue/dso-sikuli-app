package de.puettner.sikuli.dso.commands.ui;

public enum StarMenuFilter {

    ALL("entd|kunds|geolo|steak|irma"),
    EIGTH_PERCENT("8%"),
    /** IRON Geologic */
    GEO_1("Geo 1"),
    GEO_2("Geo 2"),
    GRANIT_GEOS("Geo 3|Geo 4|Geo 5|Geo 6|Geo 7|Geo 8"),
    GeneralsFilterString("GM|Var|Ans|Nus|MdK|Mar|Vet|Sen|Dra|San");

    public final String filterString;

    StarMenuFilter(String filterString) {
        this.filterString = filterString;
    }
}
