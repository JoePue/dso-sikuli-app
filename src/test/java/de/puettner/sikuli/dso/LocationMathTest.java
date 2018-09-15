package de.puettner.sikuli.dso;

import de.puettner.sikuli.dso.ui.commands.MenuTest;
import org.junit.Ignore;
import org.junit.Test;
import org.sikuli.script.Location;
import org.sikuli.script.Region;

import static de.puettner.sikuli.dso.commands.ui.SikuliCommands.dragDropBoundary;
import static org.junit.Assert.assertEquals;

public class LocationMathTest extends MenuTest {

    @Test
    @Ignore
    public void calculateSourceLocation() {
        Location result = LocationMath.calculateSourceLocation(200, -750, islandCmds.getIslandRegion());
        assertEquals(1234, result.x);
        assertEquals(865, result.y);
        result = LocationMath.calculateSourceLocation(0, -100, islandCmds.getIslandRegion());
        assertEquals(1234, result.x);
        assertEquals(865, result.y);
        //        new Location(-200, 0)
        result = LocationMath.calculateSourceLocation(-500, 0, islandCmds.getIslandRegion());
        assertEquals(115, result.x);
        assertEquals(865, result.y);
    }

    @Test
    public void calculateSourceLocationWithTestRegion() {
        // x, y, w, h
        Region testRegion = new Region(50, 60, 700, 800);
        Location actual;
        actual = LocationMath.calculateSourceLocation(0, -75, testRegion);
        assertEquals(700 - dragDropBoundary, actual.x);
        assertEquals(800 - dragDropBoundary, actual.y);
        actual = LocationMath.calculateSourceLocation(0, 75, testRegion);
        assertEquals(700 - dragDropBoundary, actual.x);
        assertEquals(60 + dragDropBoundary, actual.y);
        actual = LocationMath.calculateSourceLocation(-75, 0, testRegion);
        assertEquals(700 - dragDropBoundary, actual.x);
        assertEquals(800 - dragDropBoundary, actual.y);
        actual = LocationMath.calculateSourceLocation(75, 0, testRegion);
        assertEquals(50 + dragDropBoundary, actual.x);
        assertEquals(800 - dragDropBoundary, actual.y);
        actual = LocationMath.calculateSourceLocation(75, 75, testRegion);
        assertEquals(50 + dragDropBoundary, actual.x);
        assertEquals(60 + dragDropBoundary, actual.y);
        actual = LocationMath.calculateSourceLocation(-75, -75, testRegion);
        assertEquals(700 - dragDropBoundary, actual.x);
        assertEquals(800 - dragDropBoundary, actual.y);

    }
}
