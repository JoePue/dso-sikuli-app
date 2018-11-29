package de.puettner.sikuli.dso.adv;

import de.puettner.sikuli.dso.commands.ui.IslandCommands;
import lombok.extern.java.Log;
import org.sikuli.script.Region;

import javax.annotation.Nullable;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Log
public class BraveTailorAdventureRouter extends AdventureRouter {

    protected List<NavigationPoint> navPoints;

    public BraveTailorAdventureRouter(IslandCommands islandCmds, Region region) {
        super(islandCmds, region);
    }

    @Override
    protected void postConstruct() {
        log.info("postConstruct()");
        this.navPoints = new ArrayList<>();
        for (NavigationPoint np : BraveTailorNavPoints.values()) {
            navPoints.add(np);
        }
    }

    @Override
    public void route(NavigationPoint startingPoint, NavigationPoint targetPoint, @Nullable Dimension targetDragDropOffset,
                      @Nullable Dimension targetClickOffset) {
        this.route(startingPoint, targetPoint, targetDragDropOffset, targetClickOffset, false);
    }

    @Override
    public List<NavigationPoint> getNavigationPoints() {
        return navPoints;
    }

    public void route(NavigationPoint startingPoint, NavigationPoint targetPoint, @Nullable Dimension targetDragDropOffset, @Nullable
            Dimension targetClickOffset, boolean isRouteCheck) {
        log.info("route() " + startingPoint + " -> " + targetPoint + "(isRouteCheck: " + isRouteCheck + ")");
        Objects.requireNonNull(startingPoint, "startingPoint is null");
        Objects.requireNonNull(targetPoint, "targetPoint is null");
        log.info("route() targetDragDropOffset: " + targetDragDropOffset);
        log.info("route() targetClickOffset: " + targetClickOffset);

        if (startingPoint.getId().equals(targetPoint.getId())) {
            if (isRouteCheck) {
                return;
            }
            centerNavigationPoint(targetPoint, targetDragDropOffset, targetClickOffset);
            // nothing else to do
        } else if (startingPoint.getId().equals(1) && targetPoint.getId().equals(2)) {
            if (isRouteCheck) {
                return;
            }
            navigate(startingPoint, targetPoint, new Dimension(-800, -100), targetDragDropOffset, targetClickOffset, true, true);
        } else if (startingPoint.getId().equals(2) && targetPoint.getId().equals(1)) {
            if (isRouteCheck) {
                return;
            }
            navigate(startingPoint, targetPoint, new Dimension(800, 0), targetDragDropOffset, targetClickOffset, true, true);
        } else if (startingPoint.getId().equals(1) && targetPoint.getId().equals(3)) {
            //  navigate() NP_1 -> NP_3
            if (isRouteCheck) {
                return;
            }
            navigate(startingPoint, targetPoint, new Dimension(-800, -300), targetDragDropOffset, targetClickOffset, true, false);
            navigate(startingPoint, targetPoint, new Dimension(-600, -300), targetDragDropOffset, targetClickOffset, false, true);
        } else if (startingPoint.getId().equals(3) && targetPoint.getId().equals(1)) {
            //  navigate() NP_3 -> NP_1
            if (isRouteCheck) {
                return;
            }
            navigate(startingPoint, targetPoint, new Dimension(800, 300), targetDragDropOffset, targetClickOffset, true, false);
            navigate(startingPoint, targetPoint, new Dimension(600, 300), targetDragDropOffset, targetClickOffset, false, true);
        } else if (startingPoint.getId().equals(2) && targetPoint.getId().equals(3)) {
            if (isRouteCheck) {
                return;
            }
            navigate(startingPoint, targetPoint, new Dimension(0, -600), targetDragDropOffset, targetClickOffset, true, true);
        } else if (startingPoint.getId().equals(3) && targetPoint.getId().equals(2)) {
            if (isRouteCheck) {
                return;
            }
            navigate(startingPoint, targetPoint, new Dimension(0, 600), targetDragDropOffset, targetClickOffset, true, true);
        } else if (startingPoint.getId().equals(3) && targetPoint.getId().equals(4)) {
            if (isRouteCheck) {
                return;
            }
            navigate(startingPoint, targetPoint, new Dimension(600, -450), targetDragDropOffset, targetClickOffset, true, true);
        } else if (startingPoint.getId().equals(4) && targetPoint.getId().equals(3)) {
            if (isRouteCheck) {
                return;
            }
            navigate(startingPoint, targetPoint, new Dimension(-600, 450), targetDragDropOffset, targetClickOffset, true, true);
        } else if (startingPoint.getId().equals(2) && targetPoint.getId().equals(4)) {
            if (isRouteCheck) {
                return;
            }
            navigate(startingPoint, targetPoint, new Dimension(250, -600), targetDragDropOffset, targetClickOffset, true, false);
            navigate(startingPoint, targetPoint, new Dimension(250, -400), targetDragDropOffset, targetClickOffset, false, true);
        } else if (startingPoint.getId().equals(4) && targetPoint.getId().equals(2)) {
            //  NP_4 -> NP_2
            if (isRouteCheck) {
                return;
            }
            navigate(startingPoint, targetPoint, new Dimension(-250, 600), targetDragDropOffset, targetClickOffset, true, false);
            navigate(startingPoint, targetPoint, new Dimension(-250, 400), targetDragDropOffset, targetClickOffset, false, true);
        } else if (startingPoint.getId().equals(1) && targetPoint.getId().equals(4)) {
            //  NP_1 -> NP_4
            if (isRouteCheck) {
                return;
            }
            navigate(startingPoint, targetPoint, new Dimension(-350, -600), targetDragDropOffset, targetClickOffset, true, false);
            navigate(startingPoint, targetPoint, new Dimension(-350, -400), targetDragDropOffset, targetClickOffset, false, true);
        } else if (startingPoint.getId().equals(4) && targetPoint.getId().equals(1)) {
            //  NP_1 -> NP_1
            if (isRouteCheck) {
                return;
            }
            navigate(startingPoint, targetPoint, new Dimension(0, 600), targetDragDropOffset, targetClickOffset, true, false);
            navigate(startingPoint, targetPoint, new Dimension(350, 400), targetDragDropOffset, targetClickOffset, false, true);

        } else if (startingPoint.getId().equals(4) && targetPoint.getId().equals(5)) {
            //  NP_4 -> NP_5
            if (isRouteCheck) {
                return;
            }
            navigate(startingPoint, targetPoint, new Dimension(500, 0), targetDragDropOffset, targetClickOffset, true, true);
        } else if (startingPoint.getId().equals(5) && targetPoint.getId().equals(4)) {
            //  NP_5 -> NP_4
            if (isRouteCheck) {
                return;
            }
            navigate(startingPoint, targetPoint, new Dimension(-500, 0), targetDragDropOffset, targetClickOffset, true, true);
        } else {
            throw new IllegalStateException("Navigation from " + startingPoint.getId() + " to " + targetPoint.getId() + " is not " +
                    "possible");
        }
    }

    /**
     * @param startPoint
     * @param targetPoint
     * @param navDragDropOffset    Verschiebung um von NP-0 zu NP-1 zu gelangen.
     * @param targetDragDropOffset
     * @param targetClickOffset
     */
    public void navigate(NavigationPoint startPoint, NavigationPoint targetPoint, Dimension navDragDropOffset, Dimension
            targetDragDropOffset, Dimension
            targetClickOffset, boolean shouldCenterStart, boolean shouldCenterTarget) {
        log.info("navigate() " + startPoint + " -> " + targetPoint + ", navDragDropOffset: " + navDragDropOffset + ", " +
                "targetDragDropOffset: " + targetDragDropOffset + ", targetClickOffset: " + targetClickOffset);

        if (shouldCenterStart) {
            centerNavigationPoint(startPoint);
        }
        super.dragDrop(navDragDropOffset);
        if (shouldCenterTarget) {
            centerNavigationPoint(targetPoint, targetDragDropOffset, targetClickOffset);
        }
        super.parkMouse();
    }
}
