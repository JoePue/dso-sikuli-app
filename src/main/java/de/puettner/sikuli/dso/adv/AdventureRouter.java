package de.puettner.sikuli.dso.adv;

import de.puettner.sikuli.dso.LocationMath;
import de.puettner.sikuli.dso.commands.ui.IslandCommands;
import lombok.extern.java.Log;
import org.sikuli.script.Location;
import org.sikuli.script.Match;
import org.sikuli.script.Region;

import javax.annotation.Nullable;
import java.awt.*;
import java.util.List;
import java.util.Objects;

@Log
public abstract class AdventureRouter {

    protected final IslandCommands islandCmds;
    protected final Region region;

    public AdventureRouter(IslandCommands islandCmds, Region region) {
        this.islandCmds = islandCmds;
        this.region = region;
    }

    public abstract void routeCheck(List<AdventureStep> adventureSteps);

    protected abstract void fillNavigationPointsList();

    protected boolean centerNavigationPoint(NavigationPoint navPoint) {
        return centerNavigationPoint(navPoint, null, null);
    }

    protected boolean centerNavigationPoint(NavigationPoint navPoint, @Nullable Dimension targetDragDropOffset, @Nullable Dimension
            targetClickOffset) {
        log.info("centerNavigationPoint() navPoint: " + navPoint);
        boolean rv = false;
        islandCmds.parkMouseForMove();
        Match match = findNavPoint(navPoint);
        if (match != null) {
            Location navPointLocation = getNavPointLocation(match);
            Location regionCenterLocation = getMidpoint();
            Dimension dimension = new Dimension(navPointLocation.x - regionCenterLocation.x, navPointLocation.y - regionCenterLocation.y);
            if (targetDragDropOffset != null) {
                log.info("targetDragDropOffset: " + targetDragDropOffset);
                dimension.width = dimension.width + targetDragDropOffset.width;
                dimension.height = dimension.height + targetDragDropOffset.height;
            }
            islandCmds.dragDrop(dimension);
            if (targetClickOffset != null) {
                match = findNavPoint(navPoint);
                navPointLocation = getNavPointLocation(match);
                log.info("targetClickOffset: " + targetClickOffset);
                Location clickLocation = new Location(navPointLocation.x + targetClickOffset.width, navPointLocation.y +
                        targetClickOffset.height);
                islandCmds.hover(clickLocation);
                islandCmds.doubleClick(clickLocation);
            }
            rv = true;
        } else {
            throw new IllegalStateException("Navigation point not found");
        }
        return rv;
    }

    private Match findNavPoint(NavigationPoint navPoint) {
        Match match;
        // Nach DragDrop muss erneut gesucht werden.
        islandCmds.parkMouseInLeftUpperCorner();
        match = islandCmds.find(navPoint.getPattern(), region);
        if (match == null) {
            islandCmds.parkMouseInLeftLowerCorner();
            match = islandCmds.find(navPoint.getPattern(), region);
        }
        if (match == null) {
            islandCmds.parkMouseInRightLowerCorner();
            match = islandCmds.find(navPoint.getPattern(), region);
        }
        if (match == null) {
            islandCmds.parkMouseInRightUpperCorner();
            match = islandCmds.find(navPoint.getPattern(), region);
        }

        log.info("match: " + match);
        if (match == null) {
            throw new IllegalStateException();
        }
        return match;
    }

    private Location getNavPointLocation(Match match) {
        Location navPointLocation = new Location(match.x, match.y);
        navPointLocation.x = match.x + (match.w / 2);
        navPointLocation.y = match.y + (match.h / 2);
        return navPointLocation;
    }

    protected Location getMidpoint() {
        return LocationMath.getMidpointLocation(region);
    }

    /**
     * This method assumes a General in Attack-Mode.
     *
     * @param startNavPoint         Startpunkt
     * @param targetNavPoint        Zielpunkt
     * @param targetDragDropOffset
     * @param initialDragDropOffset Darf null sein, zum initialen verschieben hin zum Start NavPoint
     */
    protected void moveToCamp(NavigationPoint startNavPoint, NavigationPoint targetNavPoint, Dimension targetDragDropOffset, Dimension
            initialDragDropOffset) {
        Objects.requireNonNull(startNavPoint, "Missing startNavPoint");
        Objects.requireNonNull(targetNavPoint, "Missing targetNavPoint");

        if (initialDragDropOffset != null) {
            islandCmds.dragDrop(initialDragDropOffset);
        }
        NavigationPoint currentNavPoint = whereIam();
        Objects.requireNonNull(currentNavPoint, "Failed to identify starting point");

        if (!startNavPoint.equals(currentNavPoint)) {
            log.info("Go to expectedStartNavPoint ");
            route(currentNavPoint, startNavPoint, null, null);
            currentNavPoint = whereIam();
        }
        if (!startNavPoint.equals(currentNavPoint)) {
            throw new IllegalStateException("Required starting navigation point not given. expectedStartNavPoint:" +
                    startNavPoint + ", currentNavPoint: " + currentNavPoint);
        }
        route(currentNavPoint, targetNavPoint, targetDragDropOffset, null);
    }

    NavigationPoint whereIam() {
        log.info("whereIam");
        java.util.List<NavigationPoint> navPoints = getNavigationPoints();
        Match match = null;
        islandCmds.parkMouseInLeftUpperCorner();
        NavigationPoint navPoint = findCurrentNavPointOnScreen(navPoints);
        if (navPoint == null) {
            islandCmds.parkMouseInLeftLowerCorner();
            navPoint = findCurrentNavPointOnScreen(navPoints);
        }
        if (navPoint == null) {
            islandCmds.parkMouseInRightUpperCorner();
            navPoint = findCurrentNavPointOnScreen(navPoints);
        }
        if (navPoint == null) {
            islandCmds.parkMouseInRightLowerCorner();
            navPoint = findCurrentNavPointOnScreen(navPoints);
        }
        if (navPoint == null) {
            throw new IllegalStateException("The current position is unknown.");
        }
        return navPoint;
    }

    public abstract void route(NavigationPoint startingPoint, NavigationPoint targetPoint, Dimension targetDragDropOffset, Dimension
            targetNavPointClickOffset);

    public abstract List<NavigationPoint> getNavigationPoints();

    private NavigationPoint findCurrentNavPointOnScreen(List<NavigationPoint> navPoints) {
        log.info("findCurrentNavPointOnScreen()");
        Match match;
        for (NavigationPoint navPoint : navPoints) {
            match = islandCmds.find(navPoint.getPattern(), region);
            if (match != null) {
                return navPoint;
            }
        }
        return null;
    }
}
