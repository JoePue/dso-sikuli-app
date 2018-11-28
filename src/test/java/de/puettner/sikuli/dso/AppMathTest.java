package de.puettner.sikuli.dso;

import lombok.extern.java.Log;
import org.junit.Test;
import org.sikuli.script.Match;
import org.sikuli.script.Region;

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
}
