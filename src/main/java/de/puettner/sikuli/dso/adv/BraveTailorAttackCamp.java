package de.puettner.sikuli.dso.adv;

import org.sikuli.script.Pattern;

import static de.puettner.sikuli.dso.commands.ui.SikuliCommands.pattern;

public enum BraveTailorAttackCamp implements AttackCamp {

    // Die Camps müssen von Position zero sichtbar sein!!!
    CAMP_1(pattern("Brave-Tailor-Camp-1-zoom1.png").similar(0.90f)),
    CAMP_2(pattern("Brave-Tailor-Camp-2-zoom1.png").similar(0.90f)),
    CAMP_3(pattern("Brave-Tailor-Camp-3-zoom1.png").similar(0.90f)),
    CAMP_4(pattern("Brave-Tailor-Camp-4-zoom1.png").similar(0.80f)),
    CAMP_5(pattern("Brave-Tailor-Camp-5-zoom1.png").similar(0.85f)),
    // Hauptlager
    CAMP_6(pattern("Brave-Tailor-Camp-6.png").similar(0.85f)),
    // Sektor 2
    CAMP_7(pattern("Brave-Tailor-Camp-7-zoom1.png").similar(0.80f).targetOffset(50, 0)),
    CAMP_8(pattern("Brave-Tailor-Camp-8-zoom1.png").similar(0.80f)),
    CAMP_9(pattern("Brave-Tailor-Camp-9-am-zoom1.png").exact().targetOffset(-1, 21)),
    CAMP_10(pattern("Brave-Tailor-Camp-10-zoom1.png").similar(0.90f).targetOffset(-13, 18)),
    CAMP_11(pattern("Brave-Tailor-Camp-11-Zoom-1.png").similar(0.80f).targetOffset(1, 12)),
    // Sektor 3
    CAMP_12(pattern("Brave-Tailor-Camp-12-zoom1.png").similar(0.90f)),
    CAMP_13(pattern("Brave-Tailor-Camp-13-zoom1.png").similar(0.85f)),
    CAMP_14(pattern("Brave-Tailor-Camp-14-zoom1.png").similar(0.85f)),
    CAMP_15(pattern("Brave-Tailor-Camp-15-zoom1.png").similar(0.85f)),
    CAMP_16(pattern("Brave-Tailor-Camp-16-zoom1.png").similar(0.85f).targetOffset(-7, -13)),
    CAMP_17(pattern("Brave-Tailor-Camp-17-zoom1.png").similar(0.75f).targetOffset(6, 13)),
    // Sektor 4
    CAMP_18(pattern("Brave-Tailor-Camp-18-am-zoom1.png").similar(0.90f).targetOffset(-27, 19)),
    CAMP_19(pattern("Brave-Tailor-Camp-19-am-zoom1.png").similar(0.90f).targetOffset(0, 30)),
    CAMP_20(pattern("Brave-Tailor-Camp-20-am-zoom1.png").similar(0.90f)),
    CAMP_21(pattern("Brave-Tailor-Camp-21-am-zoom1.png").similar(0.90f).targetOffset(0, -9)),
    CAMP_22(pattern("Brave-Tailor-Camp-22-am-zoom1.png").similar(0.90f).targetOffset(0, 10)),
    CAMP_23(pattern("Brave-Tailor-Camp-23-am-zoom1.png").similar(0.90f).targetOffset(3, 12)),
    CAMP_24(pattern("Brave-Tailor-Camp-24-am-zoom1.png").similar(0.90f)),
    CAMP_25(pattern("Brave-Tailor-Camp-25-am-zoom1.png").similar(0.90f).targetOffset(35, -17));

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
