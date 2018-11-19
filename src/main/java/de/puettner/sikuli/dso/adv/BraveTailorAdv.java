package de.puettner.sikuli.dso.adv;

import de.puettner.sikuli.dso.DSOService;
import de.puettner.sikuli.dso.commands.ui.IslandCommands;
import de.puettner.sikuli.dso.commands.ui.StarMenu;
import de.puettner.sikuli.dso.commands.ui.StarMenuFilter;
import lombok.extern.java.Log;

import javax.annotation.Nullable;
import java.awt.*;
import java.io.File;
import java.util.List;
import java.util.Objects;

/**
 * brave tailor = tapferes Schneiderlein
 */
@Log
public class BraveTailorAdv extends Adventure {

    public static final File stateFile = new File("brave-tailor-adventure.json");

    /**
     * C'tor
     */
    protected BraveTailorAdv(IslandCommands islandCmds, StarMenu starMenu, DSOService dsoService) {
        super(islandCmds, starMenu, dsoService);
    }

    @Override
    protected void fillNavigationPointsList() {
        log.info("fillNavigationPointsList()");
        for (NavigationPoint np : BraveTailorNavPoints.values()) {
            navPoints.add(np);
        }
    }

    @Override
    public void route(NavigationPoint startingPoint, NavigationPoint targetPoint, Dimension targetDragDropOffset, Dimension
            targetClickOffset) {
        this.route(startingPoint, targetPoint, targetDragDropOffset, targetClickOffset, false);
    }

    @Override
    public void routeCheck() {
        log.info("routeCheck()");
        for (AdventureStep step : this.adventureSteps) {
            if (StepType.ATTACK.equals(step.getStepType()) || StepType.MOVE.equals(step.getStepType())) {
                try {
                    this.route(step.getStartNavPoint(), step.getTargetNavPoint(), step.getTargetDragDropOffset(), step
                            .getTargetNavPointClickOffset(), true);
                } catch (Exception e) {
                    log.info("routeCheck: " + step);
                    throw e;
                }
            }
        }
    }

    @Override
    public List<NavigationPoint> getNavigationPoints() {
        return navPoints;
    }

    @Override
    protected File getFilename() {
        return stateFile;
    }

    @Override
    protected NavigationPoint getFirstNavigationPoint() {
        return BraveTailorNavPoints.NP_1;
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
     * @param navDragDropOffset Verschiebung um von NP-0 zu NP-1 zu gelangen.
     * @param targetDragDropOffset
     * @param targetClickOffset
     */
    private void navigate(NavigationPoint startPoint, NavigationPoint targetPoint, Dimension navDragDropOffset, Dimension targetDragDropOffset, Dimension
            targetClickOffset, boolean shouldCenterStart, boolean shouldCenterTarget) {
        log.info("navigate() " + startPoint + " -> " + targetPoint + ", navDragDropOffset: " + navDragDropOffset + ", " +
                "targetDragDropOffset: " + targetDragDropOffset + ", targetClickOffset: " + targetClickOffset);

        if (shouldCenterStart) {
            centerNavigationPoint(startPoint);
        }
        islandCmds.dragDrop(navDragDropOffset);
        if (shouldCenterTarget) {
            centerNavigationPoint(targetPoint, targetDragDropOffset, targetClickOffset);
        }
        islandCmds.parkMouse();
    }

    private void prepareStarMenu() {
        super.prepareStarMenu(StarMenuFilter.GeneralsFilterString);
    }

}
