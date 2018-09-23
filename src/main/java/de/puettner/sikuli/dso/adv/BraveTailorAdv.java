package de.puettner.sikuli.dso.adv;

import de.puettner.sikuli.dso.DSOService;
import de.puettner.sikuli.dso.commands.ui.IslandCommands;
import de.puettner.sikuli.dso.commands.ui.StarMenu;
import de.puettner.sikuli.dso.commands.ui.StarMenuFilter;
import lombok.extern.java.Log;

import java.awt.*;
import java.io.File;
import java.util.List;
import java.util.Objects;

/**
 * brave tailor = tapferes Schneiderlein
 */
@Log
public class BraveTailorAdv extends Adventure {

    private final File stateFile = new File("brave-tailor-adventure.json");

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
        log.info("route() " + startingPoint + " -> " + targetPoint);
        Objects.requireNonNull(startingPoint, "startingPoint is null");
        Objects.requireNonNull(targetPoint, "targetPoint is null");
        log.info("route() targetDragDropOffset: " + targetDragDropOffset);
        log.info("route() targetClickOffset: " + targetClickOffset);

        if (startingPoint.getId().equals(targetPoint.getId())) {
            centerNavigationPoint(targetPoint, targetDragDropOffset, targetClickOffset);
            // nothing else to do
        } else if (startingPoint.getId().equals(2) && targetPoint.getId().equals(3)) {
            navigate(startingPoint, targetPoint, new Dimension(0, -600), targetDragDropOffset);
        } else if (startingPoint.getId().equals(3) && targetPoint.getId().equals(2)) {
            navigate(startingPoint, targetPoint, new Dimension(0, 600), targetDragDropOffset);
        } else {
            throw new IllegalStateException("Navigation from " + startingPoint.getId() + " to " + targetPoint.getId() + " is not " +
                    "possible");
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

    /**
     * @param startingPoint
     * @param targetPoint
     * @param targetDragDropOffset Verschiebung um von NP-0 zu NP-1 zu gelangen.
     * @param targetClickOffset
     */
    private void navigate(NavigationPoint startingPoint, NavigationPoint targetPoint, Dimension targetDragDropOffset, Dimension
            targetClickOffset) {
        log.info("navigate()");
        centerNavigationPoint(startingPoint);
        islandCmds.dragDrop(targetDragDropOffset);
        centerNavigationPoint(targetPoint, targetDragDropOffset, targetClickOffset);
    }

    private void prepareStarMenu() {
        super.prepareStarMenu(StarMenuFilter.GeneralsFilterString);
    }

}
