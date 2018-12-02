package de.puettner.sikuli.dso.adv;

import org.sikuli.script.Pattern;

import static de.puettner.sikuli.dso.commands.ui.SikuliCommands.pattern;

public enum BraveTailorNavPoints implements NavigationPoint {

    NP_1(pattern("Brave-Tailor-NavPoint-1-zoom1").similar(0.90f)),
    NP_2(pattern("Brave-Tailor-NavPoint-2-zoom1").similar(0.90f)),
    NP_3(pattern("Brave-Tailor-NavPoint-3-zoom1").similar(0.90f)),
    NP_4(pattern("Brave-Tailor-NavPoint-4-zoom1").similar(0.99f)),
    NP_5(pattern("Brave-Tailor-NavPoint-5-zoom1").similar(0.99f));

    private final Pattern pattern;

    BraveTailorNavPoints(Pattern pattern) {
        this.pattern = pattern;
    }

    public Pattern getPattern() {
        return pattern;
    }

    public Integer getId() {
        return this.ordinal() + 1;
    }

}
