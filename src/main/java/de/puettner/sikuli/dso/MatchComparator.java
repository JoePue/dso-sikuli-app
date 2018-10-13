package de.puettner.sikuli.dso;

import org.sikuli.script.Match;

import java.util.Comparator;

public class MatchComparator implements Comparator<Match> {
    @Override
    public int compare(Match m1, Match m2) {
        double distance = AppMath.distance(m1, m2);
        return (int) distance;
    }
}
