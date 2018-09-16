package de.puettner.sikuli.dso;

import de.puettner.sikuli.dso.commands.ui.SikuliCommands;
import lombok.extern.java.Log;
import org.sikuli.script.Location;
import org.sikuli.script.Region;

@Log
public abstract class LocationMath {

    public static Location calculateSourceLocation(int xOffset, int yOffset, Region region) {
        int xSource = region.w - SikuliCommands.dragDropBoundary, ySource = region.h - SikuliCommands.dragDropBoundary;
        if (xOffset > 0) {
            xSource = region.x + SikuliCommands.dragDropBoundary;
        }
        if (yOffset > 0) {
            ySource = region.y + SikuliCommands.dragDropBoundary;
        }
        Location sourceLocation = new Location(xSource, ySource);
        return sourceLocation;
    }

    public static Location calculateTargetLocation(int xOffset, int yOffset, Location sourceLocation) {
        Location targetLocation = new Location(sourceLocation.x + xOffset, sourceLocation.y + yOffset);
        return targetLocation;
    }

    public static Location getMidpointLocation(Region region) {
        return new Location(region.x + (region.w / 2), region.y + (region.h / 2));
    }
}
