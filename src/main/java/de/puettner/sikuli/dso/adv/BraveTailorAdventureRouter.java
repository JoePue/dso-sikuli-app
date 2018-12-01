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
        postConstruct();
    }

    protected void postConstruct() {
        log.info("postConstruct()");
        this.navPoints = new ArrayList<>();
        for (NavigationPoint np : BraveTailorNavPoints.values()) {
            navPoints.add(np);
        }
    }

    @Override
    public void route(NavigationPoint startingPoint, NavigationPoint targetPoint, @Nullable Dimension targetDragDropOffset,
                      @Nullable Dimension targetClickOffset, Dimension initialDragDropOffset) {
        this.route(startingPoint, targetPoint, targetDragDropOffset, targetClickOffset, false, initialDragDropOffset);
    }

    @Override
    public List<NavigationPoint> getNavigationPoints() {
        return navPoints;
    }

    public void route(NavigationPoint startingPoint, NavigationPoint targetPoint, @Nullable Dimension targetDragDropOffset, @Nullable
            Dimension targetClickOffset, boolean isRouteCheck, Dimension initialDragDropOffset) {
        log.info("route() " + startingPoint + " -> " + targetPoint + "(isRouteCheck: " + isRouteCheck + ")");
        Objects.requireNonNull(startingPoint, "startingPoint is null");
        Objects.requireNonNull(targetPoint, "targetPoint is null");
        log.fine("route() targetDragDropOffset: " + targetDragDropOffset);
        log.fine("route() targetClickOffset: " + targetClickOffset);

        if (startingPoint.getId().equals(targetPoint.getId())) {
            if (isRouteCheck) {
                return;
            }
            navigate(startingPoint, targetPoint, new Dimension(0, 0), targetDragDropOffset, targetClickOffset, initialDragDropOffset);
        } else if (startingPoint.getId().equals(1) && targetPoint.getId().equals(2)) {
            if (isRouteCheck) {
                return;
            }
            navigate(startingPoint, targetPoint, new Dimension(-800, -100), targetDragDropOffset, targetClickOffset, initialDragDropOffset);
        } else if (startingPoint.getId().equals(2) && targetPoint.getId().equals(1)) {
            if (isRouteCheck) {
                return;
            }
            navigate(startingPoint, targetPoint, new Dimension(800, 0), targetDragDropOffset, targetClickOffset, initialDragDropOffset);
        } else if (startingPoint.getId().equals(1) && targetPoint.getId().equals(3)) {
            //  navigate() NP_1 -> NP_3
            if (isRouteCheck) {
                return;
            }
            navigate(startingPoint, targetPoint, new Dimension(-1400, -600), targetDragDropOffset, targetClickOffset,
                    initialDragDropOffset);
        } else if (startingPoint.getId().equals(3) && targetPoint.getId().equals(1)) {
            //  navigate() NP_3 -> NP_1
            if (isRouteCheck) {
                return;
            }
            navigate(startingPoint, targetPoint, new Dimension(1400, 300), targetDragDropOffset, targetClickOffset, initialDragDropOffset);
        } else if (startingPoint.getId().equals(2) && targetPoint.getId().equals(3)) {
            if (isRouteCheck) {
                return;
            }
            navigate(startingPoint, targetPoint, new Dimension(0, -600), targetDragDropOffset, targetClickOffset, initialDragDropOffset);
        } else if (startingPoint.getId().equals(3) && targetPoint.getId().equals(2)) {
            if (isRouteCheck) {
                return;
            }
            navigate(startingPoint, targetPoint, new Dimension(0, 600), targetDragDropOffset, targetClickOffset, initialDragDropOffset);
        } else if (startingPoint.getId().equals(3) && targetPoint.getId().equals(4)) {
            if (isRouteCheck) {
                return;
            }
            navigate(startingPoint, targetPoint, new Dimension(600, -450), targetDragDropOffset, targetClickOffset, initialDragDropOffset);
        } else if (startingPoint.getId().equals(4) && targetPoint.getId().equals(3)) {
            if (isRouteCheck) {
                return;
            }
            navigate(startingPoint, targetPoint, new Dimension(-600, 450), targetDragDropOffset, targetClickOffset, initialDragDropOffset);
        } else if (startingPoint.getId().equals(2) && targetPoint.getId().equals(4)) {
            if (isRouteCheck) {
                return;
            }
            navigate(startingPoint, targetPoint, new Dimension(500, -1000), targetDragDropOffset, targetClickOffset, initialDragDropOffset);
        } else if (startingPoint.getId().equals(4) && targetPoint.getId().equals(2)) {
            //  NP_4 -> NP_2
            if (isRouteCheck) {
                return;
            }
            navigate(startingPoint, targetPoint, new Dimension(-500, 1000), targetDragDropOffset, targetClickOffset, initialDragDropOffset);
        } else if (startingPoint.getId().equals(1) && targetPoint.getId().equals(4)) {
            //  NP_1 -> NP_4
            if (isRouteCheck) {
                return;
            }
            navigate(startingPoint, targetPoint, new Dimension(-700, -1000), targetDragDropOffset, targetClickOffset, initialDragDropOffset);
        } else if (startingPoint.getId().equals(4) && targetPoint.getId().equals(1)) {
            //  NP_1 -> NP_1
            if (isRouteCheck) {
                return;
            }
            navigate(startingPoint, targetPoint, new Dimension(350, 1000), targetDragDropOffset, targetClickOffset, initialDragDropOffset);
        } else if (startingPoint.getId().equals(4) && targetPoint.getId().equals(5)) {
            //  NP_4 -> NP_5
            if (isRouteCheck) {
                return;
            }
            navigate(startingPoint, targetPoint, new Dimension(500, 0), targetDragDropOffset, targetClickOffset, initialDragDropOffset);
        } else if (startingPoint.getId().equals(5) && targetPoint.getId().equals(4)) {
            //  NP_5 -> NP_4
            if (isRouteCheck) {
                return;
            }
            navigate(startingPoint, targetPoint, new Dimension(-500, 0), targetDragDropOffset, targetClickOffset, initialDragDropOffset);
        } else {
            throw new IllegalStateException("Navigation from " + startingPoint.getId() + " to " + targetPoint.getId() + " is not " +
                    "possible");
        }
    }

}
