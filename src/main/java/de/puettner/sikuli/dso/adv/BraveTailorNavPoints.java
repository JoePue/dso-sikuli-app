package de.puettner.sikuli.dso.adv;

import org.sikuli.script.Pattern;

import static de.puettner.sikuli.dso.commands.ui.SikuliCommands.pattern;

public enum BraveTailorNavPoints implements NavigationPoint {

    NP_1(null, pattern("Brave-Tailor-NavPoint-1-zoom1").similar(0.90)),
    NP_2(null, pattern("Brave-Tailor-NavPoint-2-zoom1").similar(0.90)),
    NP_3(null, pattern("Brave-Tailor-NavPoint-3-zoom1").similar(0.90)),
    NP_4(null, pattern("Brave-Tailor-NavPoint-4-zoom1").similar(0.99));

    private final Integer id;
    private final Pattern pattern;

    BraveTailorNavPoints(Integer id, Pattern pattern) {
        this.pattern = pattern;
        this.id = id;
    }

    public Pattern getPattern() {
        return pattern;
    }

    public Integer getId() {
        return this.ordinal() + 1;
    }

}
