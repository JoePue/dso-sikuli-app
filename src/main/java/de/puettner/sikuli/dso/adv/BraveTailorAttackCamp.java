package de.puettner.sikuli.dso.adv;

import org.sikuli.script.Pattern;

import java.awt.*;

import static de.puettner.sikuli.dso.adv.BraveTailorNavPoints.*;
import static de.puettner.sikuli.dso.commands.ui.SikuliCommands.pattern;

public enum BraveTailorAttackCamp implements AttackCamp {

    // Die Camps müssen von Position zero sichtbar sein!!!
    CAMP_1(pattern("Brave-Tailor-Camp-1-zoom1.png").similar(0.90), NP_1, null),
    CAMP_2(pattern("Brave-Tailor-Camp-2-zoom1.png").similar(0.90), NP_1, null),
    CAMP_3(pattern("Brave-Tailor-Camp-3-zoom1.png").similar(0.90), NP_2, null),
    CAMP_4(pattern("Brave-Tailor-Camp-4.png").similar(0.85), NP_2, null),
    CAMP_5(pattern("Brave-Tailor-Camp-5.png").similar(0.85), NP_2, null),
    // Hauptlager
    CAMP_6(pattern("Brave-Tailor-Camp-6.png").similar(0.85), null, null),
    // Sektor 2
    //    MOVE_POINT_1(pattern("move-ref-point-sector2.png").similar(0.90), null, null),
    CAMP_7(pattern("Brave-Tailor-Camp-7.png").similar(0.95).targetOffset(65, 26), null, null),
    CAMP_8(pattern("Brave-Tailor-Camp-8-zoom1.png").similar(0.96), NP_3, new Dimension(-200, 200)),
    CAMP_9(pattern("Brave-Tailor-Camp-9-am-zoom1").similar(0.99), NP_3, new Dimension(-200, 0)),
    CAMP_10(pattern("Brave-Tailor-Camp-10-zoom1.png").similar(0.90).targetOffset(-13, 18), NP_3, new Dimension(-100, -100)),
    CAMP_11(pattern("Brave-Tailor-Camp-10-zoom1.png").similar(0.90).targetOffset(17, -25), NP_3, new Dimension(-100, -100));


    /** pattern of camp */
    private final Pattern pattern;
    /* the target camp is near by this navigation point */
    //    private final NavigationPoint navPoint;
    /* dragDrop from navPoint to attack camp */
    private final Dimension targetOffset;

    BraveTailorAttackCamp(Pattern pattern, NavigationPoint navPoint, Dimension targetOffset) {
        this.pattern = pattern;
        this.targetOffset = targetOffset;
        //        this.navPoint = navPoint;
    }

    @Override
    public Pattern getPattern() {
        return pattern;
    }

    //    @Override
    //    public NavigationPoint getNavigationPoint() {
    //        return navPoint;
    //    }

    //    @Override
    //    public Dimension getTargetDragDropOffset() {
    //        return targetOffset;
    //    }

}
