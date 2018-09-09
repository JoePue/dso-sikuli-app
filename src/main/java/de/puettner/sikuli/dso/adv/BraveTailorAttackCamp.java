package de.puettner.sikuli.dso.adv;

import org.sikuli.script.Location;
import org.sikuli.script.Pattern;

import static de.puettner.sikuli.dso.commands.ui.SikuliCommands.pattern;

public enum BraveTailorAttackCamp implements AttackCamp {

    // Die Camps müssen von Position zero sichtbar sein!!!
    CAMP_1(pattern("Brave-Tailor-Camp-1-2.png").exact().targetOffset(-88, 107), null),
    CAMP_2(pattern("Brave-Tailor-Camp-2.png").exact().targetOffset(-5, 69), new Location(-500, 0)),
    CAMP_3(pattern("Brave-Tailor-Camp-3.png").exact().targetOffset(-17, 17), new Location(-500, 0)),
    CAMP_4(pattern("Brave-Tailor-Camp-4.png").exact(), new Location(-700, 0)),
    CAMP_5(pattern("Brave-Tailor-Camp-5.png").exact(), new Location(-700, 0));
    // Rek(200), Kan(85),
    private final Pattern pattern;
    private final Location dragNDrop;

    BraveTailorAttackCamp(Pattern pattern, Location dragNDrop) {
        this.pattern = pattern;
        this.dragNDrop = dragNDrop;
    }

    @Override
    public Pattern getPattern() {
        return pattern;
    }

    @Override
    public Location getDragNDrop() {
        return dragNDrop;
    }
}
