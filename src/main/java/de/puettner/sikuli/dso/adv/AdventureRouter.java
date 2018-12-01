package de.puettner.sikuli.dso.adv;

import de.puettner.sikuli.dso.AppMath;
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

    private final IslandCommands islandCmds;
    private final Region adventureRegion;

    public AdventureRouter(IslandCommands islandCmds, Region adventureRegion) {
        this.islandCmds = islandCmds;
        this.adventureRegion = adventureRegion;
    }

    protected boolean centerNavigationPoint(NavigationPoint navigationPoint) {
        Match match = findNavPoint(navigationPoint);
        if (match != null) {
            return centerNavigationPoint(match);
        } else {
            throw new IllegalStateException("Navigation point not found");
        }
    }

    protected Match findNavPoint(NavigationPoint navPoint) {
        log.info("findNavPoint()");
        Match match;
        // Nach DragDrop muss erneut gesucht werden.
        islandCmds.parkMouseInLeftUpperCorner();
        match = islandCmds.find(navPoint.getPattern(), adventureRegion);
        if (match == null) {
            islandCmds.parkMouseInLeftLowerCorner();
            match = islandCmds.find(navPoint.getPattern(), adventureRegion);
        }
        if (match == null) {
            islandCmds.parkMouseInRightLowerCorner();
            match = islandCmds.find(navPoint.getPattern(), adventureRegion);
        }
        if (match == null) {
            islandCmds.parkMouseInRightUpperCorner();
            match = islandCmds.find(navPoint.getPattern(), adventureRegion);
        }

        log.info("match: " + match);
        if (match == null) {
            throw new IllegalStateException();
        }
        return match;
    }

    protected boolean centerNavigationPoint(Match match) {
        log.info("centerNavigationPoint()");
        dragDrop(AppMath.calculateCenterDimension(getMidpoint(), convertMatchToLocation(match)));
        return true;
    }

    public void dragDrop(Dimension dimension) {
        final int maxDim = 700;
        islandCmds.parkMouseForMove();
        List<Dimension> splittedDimensions = AppMath.splitDimension(dimension, maxDim);
        for (Dimension partialDimension : splittedDimensions) {
            islandCmds.dragDrop(partialDimension);
        }
    }

    protected Location getMidpoint() {
        return LocationMath.getMidpointLocation(adventureRegion);
    }

    public static Location convertMatchToLocation(Match match) {
        Location location = new Location(match.x, match.y);
        location.x = match.x + (match.w / 2);
        location.y = match.y + (match.h / 2);
        return location;
    }

    /**
     * This method assumes a General in Attack-Mode.
     *
     * @param startNavPoint         Startpunkt
     * @param targetNavPoint        Zielpunkt
     * @param targetDragDropOffset
     * @param initialDragDropOffset Darf null sein, zum initialen Verschieben hin zum Start NavPoint
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
            route(currentNavPoint, startNavPoint, null, null, initialDragDropOffset);
            currentNavPoint = whereIam();
        }
        if (!startNavPoint.equals(currentNavPoint)) {
            throw new IllegalStateException("Required starting navigation point not given. expectedStartNavPoint:" +
                    startNavPoint + ", currentNavPoint: " + currentNavPoint);
        }
        route(currentNavPoint, targetNavPoint, targetDragDropOffset, null, initialDragDropOffset);
    }

    NavigationPoint whereIam() {
        log.info("whereIam");
        List<NavigationPoint> navPoints = getNavigationPoints();
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
            targetNavPointClickOffset, Dimension initialDragDropOffset);

    public abstract List<NavigationPoint> getNavigationPoints();

    private NavigationPoint findCurrentNavPointOnScreen(List<NavigationPoint> navPoints) {
        log.info("findCurrentNavPointOnScreen()");
        Match match;
        for (NavigationPoint navPoint : navPoints) {
            match = islandCmds.find(navPoint.getPattern(), adventureRegion);
            if (match != null) {
                return navPoint;
            }
        }
        return null;
    }

    public void routeCheck(java.util.List<AdventureStep> adventureSteps) {
        log.info("routeCheck()");
        for (AdventureStep step : adventureSteps) {
            if (StepType.ATTACK.equals(step.getStepType()) || StepType.MOVE.equals(step.getStepType())) {
                try {
                    route(step.getStartNavPoint(), step.getTargetNavPoint(), step.getTargetDragDropOffset(), step
                            .getTargetNavPointClickOffset(), true, step.getInitialDragDropOffset());
                } catch (Exception e) {
                    log.info("routeCheck: " + step);
                    throw e;
                }
            }
        }
    }

    public abstract void route(NavigationPoint startingPoint, NavigationPoint targetPoint, Dimension targetDragDropOffset, Dimension
            targetNavPointClickOffset, boolean isRouteCheck, Dimension initialDragDropOffset);

    public void highlightRegion() {
        islandCmds.highlightRegion(adventureRegion);
    }

    /**
     * @param startPoint
     * @param targetPoint
     * @param navDragDropOffset    Verschiebung um von NP-0 zu NP-1 zu gelangen.
     * @param targetDragDropOffset
     * @param targetClickOffset
     */
    public void navigate(NavigationPoint startPoint, NavigationPoint targetPoint, Dimension navDragDropOffset, Dimension
            targetDragDropOffset, Dimension targetClickOffset, Dimension initialDragDropOffset) {
        log.info("navigate() " + startPoint + " -> " + targetPoint + ", navDragDropOffset: " + navDragDropOffset + ", " +
                "targetDragDropOffset: " + targetDragDropOffset + ", targetClickOffset: " + targetClickOffset);

        if (initialDragDropOffset != null) {
            dragDrop(initialDragDropOffset);
        }
        Match startPointMatch = findNavPoint(startPoint);
        if (startPointMatch != null) {
            // startPointDim entspricht y,x zur Zentrierung des startPoint
            Dimension startPointDim = AppMath.calculateCenterDimension(getMidpoint(), convertMatchToLocation(startPointMatch));

            Dimension diffDimension = AppMath.add(navDragDropOffset, startPointDim);
            dragDrop(diffDimension);
            //hoverMidpoint(); // Debugging only
            if (startPoint.equals(targetPoint)) {
                if (targetDragDropOffset != null) {
                    dragDrop(targetDragDropOffset);
                }
            } else {
                // Nur wenn der Zielpunkt ungleich dem Startpunkt ist. Bsp Lager liegt x,y  verschoben vom Startpkt
                Match targetPointMatch = findNavPoint(targetPoint);
                Dimension targetPointDim = AppMath.calculateCenterDimension(getMidpoint(), convertMatchToLocation(targetPointMatch));
                if (targetDragDropOffset == null) {
                    targetDragDropOffset = new Dimension(0, 0);
                }
                Dimension targetPointDiffDimension = AppMath.add(targetDragDropOffset, targetPointDim);
                dragDrop(targetPointDiffDimension);
            }
            processTargetClickOffset(targetPoint, targetClickOffset);
        } else {
            throw new IllegalStateException("Navigation point not found");
        }
        parkMouse();
    }

    protected void processTargetClickOffset(NavigationPoint navPoint, @Nullable Dimension targetClickOffset) {
        Match match;
        Location navPointLocation;
        if (targetClickOffset != null) {
            match = findNavPoint(navPoint);
            navPointLocation = convertMatchToLocation(match);
            log.info("targetClickOffset: " + targetClickOffset);
            Location clickLocation = new Location(navPointLocation.x + targetClickOffset.width, navPointLocation.y +
                    targetClickOffset.height);
            islandCmds.hover(clickLocation);
            islandCmds.doubleClick(clickLocation);
        }
    }

    public void parkMouse() {
        islandCmds.parkMouse();
    }

    /**
     * Maus über Mittelpunkt der AdventureRegion
     */
    private void hoverMidpoint() {
        islandCmds.hover(getMidpoint());
    }
}
