package de.puettner.sikuli.dso;

import org.sikuli.script.Match;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

public class AppMath {
    public static double distance(Match match, Match lastMatch) {
        if (match == null || lastMatch == null) {
            throw new IllegalArgumentException();
        }
        return sqrt(pow(match.x - lastMatch.x, 2) + pow(match.y - lastMatch.y, 2));
    }
}
