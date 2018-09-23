package de.puettner.sikuli.dso.adv;

import org.sikuli.script.Pattern;

import static de.puettner.sikuli.dso.commands.ui.SikuliCommands.pattern;

public enum BraveTailorAttackCamp implements AttackCamp {

    // Die Camps müssen von Position zero sichtbar sein!!!
    CAMP_1(pattern("Brave-Tailor-Camp-1-zoom1.png").similar(0.90)),
    CAMP_2(pattern("Brave-Tailor-Camp-2-zoom1.png").similar(0.90)),
    CAMP_3(pattern("Brave-Tailor-Camp-3-zoom1.png").similar(0.90)),
    CAMP_4(pattern("Brave-Tailor-Camp-4-zoom1.png").similar(0.80)),
    CAMP_5(pattern("Brave-Tailor-Camp-5-zoom1.png").similar(0.85)),
    // Hauptlager
    CAMP_6(pattern("Brave-Tailor-Camp-6.png").similar(0.85)),
    // Sektor 2
    CAMP_7(pattern("Brave-Tailor-Camp-7-zoom1.png").similar(0.80).targetOffset(50, 0)),
    CAMP_8(pattern("Brave-Tailor-Camp-8-zoom1.png").similar(0.80)),
    CAMP_9(pattern("Brave-Tailor-Camp-9-am-zoom1").similar(0.99)),
    CAMP_10(pattern("Brave-Tailor-Camp-10-zoom1.png").similar(0.90).targetOffset(-13, 18)),
    CAMP_11(pattern("Brave-Tailor-Camp-11-Zoom-1.png").similar(0.80).targetOffset(1, 12)),
    // Sektor 3
    CAMP_12(pattern("Brave-Tailor-Camp-12-zoom1.png").similar(0.90)),
    CAMP_13(pattern("Brave-Tailor-Camp-13-zoom1.png").similar(0.85)),
    CAMP_14(pattern("Brave-Tailor-Camp-14-zoom1.png").similar(0.85)),
    CAMP_15(pattern("Brave-Tailor-Camp-15-zoom1.png").similar(0.85)),
    CAMP_16(pattern("Brave-Tailor-Camp-16-zoom1.png").similar(0.85).targetOffset(-7, -13)),
    CAMP_17(pattern("Brave-Tailor-Camp-17-zoom1.png").similar(0.75).targetOffset(6, 13)),;

    /** pattern of camp */
    private final Pattern pattern;

    BraveTailorAttackCamp(Pattern pattern) {
        this.pattern = pattern;
    }

    @Override
    public Pattern getPattern() {
        return pattern;
    }
}
