package de.puettner.sikuli.dso;

import lombok.extern.java.Log;
import org.sikuli.script.Location;
import org.sikuli.script.Match;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

@Log
public class AppMath {
    public static double distance(Match match, Match lastMatch) {
        if (match == null || lastMatch == null) {
            throw new IllegalArgumentException();
        }
        return sqrt(pow(match.x - lastMatch.x, 2) + pow(match.y - lastMatch.y, 2));
    }

    /**
     * Berechnet die Dimension zur Zentrierung des übergebenen Punktes.
     *
     * @param midpoint Mittelpunkt
     * @param location
     * @return
     */
    public static Dimension calculateCenterDimension(Location midpoint, Location location) {
        log.finest("midpoint: " + midpoint);
        log.finest("location: " + location);
        Dimension dimension = new Dimension(location.x - midpoint.x, location.y - midpoint.y);
        log.finest("dimension: " + dimension);
        return dimension;
    }

    public static Dimension add(Dimension left, Dimension right) {
        log.finest("add() leftArg: " + left);
        log.finest("add() rightArg: " + right);
        Dimension result = new Dimension(left.width + right.width, left.height + right.height);
        log.finest("result() result: " + result);
        return result;
    }

    public static Location add(Match match, Dimension dimension) {
        return new Location(match.x + dimension.width, match.y + dimension.height);
    }

    public static List<Dimension> splitDimension(final Dimension dimension, final int maxDim) {
        int absW = Math.abs(dimension.width), absH = Math.abs(dimension.height);
        boolean isNegativW = dimension.width < 0, isNegativH = dimension.height < 0;
        if (absW <= maxDim && absH <= maxDim) {
            return Collections.singletonList(dimension);
        }
        List<Dimension> dims = new ArrayList<>();
        while (absW > 0 || absH > 0) {
            Dimension newDim = new Dimension();
            if (absW > maxDim) {
                newDim.width = maxDim;
                absW = absW - maxDim;
            } else {
                newDim.width = absW;
                absW = 0;
            }
            if (absH > maxDim) {
                newDim.height = maxDim;
                absH = absH - maxDim;
            } else {
                newDim.height = absH;
                absH = 0;
            }
            if (isNegativW) {
                newDim.width = newDim.width * -1;
            }
            if (isNegativH) {
                newDim.height = newDim.height * -1;
            }
            dims.add(newDim);
        }
        return dims;
    }
}
