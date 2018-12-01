package de.puettner.sikuli.dso;

import lombok.extern.java.Log;
import org.junit.Test;
import org.sikuli.script.Match;
import org.sikuli.script.Region;

import java.awt.*;

@Log
public class AppMathTest {

    private AppMath appMath = new AppMath();

    @Test
    public void distance() {
        Match match = new Match(Region.create(1, 1, 1, 1), 1);
        Match match2 = new Match(Region.create(10, 1, 1, 1), 1);
        double result = AppMath.distance(match, match2);
        log.info("distance: " + result);
        System.out.println(result);
    }

    @Test
    public void splitDimension() {
        Dimension startDimension = new Dimension(0, 0);
        java.util.List<Dimension> dims = AppMath.splitDimension(startDimension, 50);
        for (Dimension splitDim : dims) {
            log.info("" + splitDim);
        }
    }

}
