package de.puettner.sikuli.dso.adv;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import de.puettner.sikuli.dso.DSOService;
import de.puettner.sikuli.dso.LocationMath;
import de.puettner.sikuli.dso.commands.ui.IslandCommands;
import de.puettner.sikuli.dso.commands.ui.MenuBuilder;
import de.puettner.sikuli.dso.commands.ui.StarMenu;
import de.puettner.sikuli.dso.commands.ui.StarMenuFilter;
import lombok.extern.java.Log;
import org.sikuli.script.Location;
import org.sikuli.script.Match;
import org.sikuli.script.Region;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Log
public abstract class Adventure {

    private static final ObjectMapper objectMapper = new ObjectMapper();
    protected final Region region;
    protected final List<NavigationPoint> navPoints = new ArrayList<>();
    private final StarMenu starMenu;
    private final DSOService dsoService;
    protected List<AdventureAttackStep> adventureSteps = new ArrayList<>();
    protected IslandCommands islandCmds;
    protected GeneralMenu generalMenu;

    protected Adventure(IslandCommands islandCmds, StarMenu starMenu, DSOService dsoService) {
        this.islandCmds = islandCmds;
        this.starMenu = starMenu;
        this.generalMenu = MenuBuilder.build().buildGeneralMenu();
        this.region = islandCmds.getIslandRegion();
        this.dsoService = dsoService;

        this.fillNavigationPointsList();

        SimpleModule module = new SimpleModule();
        module.addDeserializer(AttackCamp.class, new AttackCampDeserializer());
        objectMapper.registerModule(module);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    protected abstract void fillNavigationPointsList();

    public abstract void route(NavigationPoint startingPoint, NavigationPoint destinationPoint);

    public abstract List<NavigationPoint> getNavigationPoints();

    protected abstract File getFilename();

    public void play() {
        log.info("play");
        this.restoreState();
        try {
            for (AdventureAttackStep step : this.adventureSteps) {
                if (AdventureStepState.PENDING.equals(step.getState())) {
                    if (StepType.ATTACK.equals(step.getStepType())) {
                        throw new NotImplementedException();
                    } else {
                        throw new IllegalStateException("Unsupported type: " + step.getStepType());
                    }
                }
            }
            for (AdventureAttackStep step : this.adventureSteps) {
                if (AdventureStepState.OPEN.equals(step.getState())) {
                    if (StepType.ATTACK.equals(step.getStepType())) {
                        log.info(step.toString());
                        if (attack(step)) {
                            saveState(step, AdventureStepState.PENDING);
                        } else {
                            throw new IllegalStateException("attack step failed");
                        }
                    } else {
                        throw new IllegalStateException("Unsupported type: " + step.getStepType());
                    }
                }
            }
            log.info("End of Adventure reached");
        } finally {
            saveState();
        }
    }

    private boolean attack(AdventureAttackStep step) {
        log.info("attack()");
        boolean rv = false;
        rv = prepareAttack(step.getCamp(), step.getGeneral(), step.getGeneralName(), step.getUnits());
        if (rv) {
            rv = clickAttackButton();
            islandCmds.sleep();
            if (rv) {
                moveToCamp(step.getCamp(), step.getStartNavPoint());
                if (rv) {
                    rv = clickAttackCamp(step.getCamp());
                } else {
                    log.severe("Clicking attack camp failed");
                }
            } else {
                log.severe("Clicking the attack button failed");
            }
        } else {
            log.severe("attack preparation failed");
        }
        return rv;
    }

    public void restoreState() {
        log.info("restoreState()");
        List<AdventureAttackStep> list;
        try {
            //            objectMapper.enableDefaultTyping();
            //            TypeReference typeRef = new TypeReference<ArrayList<AdventureAttackStep>>() {};
            AdventureState state = objectMapper.readValue(getFilename(), AdventureState.class);
            list = state.getAdventureSteps();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (list == null) {
            list = new ArrayList<>();
        }
        this.adventureSteps = list;
    }

    private void saveState(AdventureAttackStep step, AdventureStepState state) {
        step.setState(state);
        this.saveState();
    }

    public void saveState() {
        log.info("saveState()");
        try {
            AdventureState state = new AdventureState();
            state.setAdventureSteps(this.adventureSteps);
            objectMapper.writeValue(getFilename(), state);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @return
     */
    protected boolean prepareAttack(AttackCamp camp, GeneralType general, String generalName, AttackUnit[] units) {
        log.info("prepareAttack() " + camp);
        boolean rv = false;
        // gotoPosOneAndZoomOut();
        if (openGeneralMenu(general, generalName)) {
            rv = setupGeneral(units);
            // TODO JPU Implement a method to check the setup
        }
        return rv;
    }

    private boolean setupGeneral(AttackUnit[] units) {
        log.info("setupGeneral");
        return generalMenu.setupAttackUnits(units);
    }

    private boolean campExists(AttackCamp camp) {
        log.info("campExists()");
        //        if (camp.getDragNDrop() != null) {
        //            islandCmds.dragDrop(camp.getDragNDrop());
        //        }
        Match match = islandCmds.find(camp.getPattern(), region);
        if (match == null) {
            return false;
        }
        match.hover();
        return true;
    }

    /**
     * This method assumes a open general menu.
     */
    protected boolean clickAttackButton() {
        log.info("clickAttackButton()");
        boolean rv = false;
        if (generalMenu.clickAttackBtn()) {
            rv = true;
        } else {
            log.severe("Missing Attack Btn");
        }
        return rv;
    }

    protected boolean centerNavigationPoint(NavigationPoint navPoint) {
        log.info("centerNavigationPoint()");
        boolean rv = false;
        Match match = islandCmds.find(navPoint.getPattern(), region);
        if (match != null) {
            Location navPointLocation = new Location(match.x, match.y);
            navPointLocation.x = match.x + (match.w / 2);
            navPointLocation.y = match.y + (match.h / 2);
            Location regionCenterLocation = getMidpoint();
            // System.out.println("match: x=" + match.x + ", y=" + match.y + ", w=" + match.w + ", h=" + match.h);
            Dimension dimension = new Dimension(navPointLocation.x - regionCenterLocation.x, navPointLocation.y - regionCenterLocation.y);
            islandCmds.dragDrop(dimension);
            rv = true;
        } else {
            throw new IllegalStateException("Navigation point is missing");
        }
        return rv;
    }

    protected Location getMidpoint() {
        return LocationMath.getMidpointLocation(region);
    }

    public void hoverRegionCenter() {
        islandCmds.hover(getMidpoint());
    }

    /**
     * This method assumes a General in Attack-Mode.
     */
    protected boolean clickAttackCamp(AttackCamp camp) {
        log.info("clickAttackCamp");
        boolean rv = false;
        islandCmds.parkMouse();
        Match match = islandCmds.find(camp.getPattern(), region);
        if (match != null) {
            match.hover();
            islandCmds.sleepX(5);
            match.doubleClick(); // Angriff starten
            islandCmds.sleepX(2);
            rv = true;
        } else {
            log.severe("Camp not found: " + camp);
        }
        return rv;
    }

    /**
     * This method assumes a General in Attack-Mode.
     */
    protected void moveToCamp(AttackCamp camp, NavigationPoint expectedStartNavPoint) {
        Objects.requireNonNull(camp, "Missing camp");
        Objects.requireNonNull(camp.getNavigationPoint(), "Missing destination");

        NavigationPoint currentNavPoint = whereIam();
        Objects.requireNonNull(currentNavPoint, "Failed to identify starting point");
        if (!expectedStartNavPoint.equals(currentNavPoint)) {
            throw new IllegalStateException("Required starting navigation point not given. expectedStartNavPoint:" +
                    expectedStartNavPoint + ", currentNavPoint: " + currentNavPoint);
        }
        route(currentNavPoint, camp.getNavigationPoint());
    }

    NavigationPoint whereIam() {
        List<NavigationPoint> navPoints = getNavigationPoints();
        Match match = null;
        for (NavigationPoint navPoint : navPoints) {
            match = islandCmds.find(navPoint.getPattern(), region);
            if (match != null) {
                return navPoint;
            }
        }
        throw new IllegalStateException("The current position is unknown.");
    }

    void moveGeneral(GeneralType general, String generalName, NavigationPoint navPoint, Location moveOffset) {
        gotoPosOneAndZoomOut();
        if (openGeneralMenu(general, generalName)) {
            islandCmds.sleepX(5);
            // unsetAllUnits();
            if (generalMenu.clickMoveBtn()) {
                gotoPosOneAndZoomOut();

                // islandCmds.dragDrop(navPoint.getDragNDrop());
                // Find ref point and place Gen
                Match match = islandCmds.find(navPoint.getPattern(), region);
                if (match == null) {
                    log.warning("Move point not found");
                    islandCmds.sleepX(10);
                }
                match = islandCmds.find(navPoint.getPattern(), region);
                if (match == null) {
                    throw new IllegalStateException("Move point not found");
                }
                Location moveLocation = new Location(match.x + (match.w / 2) + moveOffset.x, match.y + (match.h / 2) + moveOffset.y);
                islandCmds.hover(moveLocation);
                islandCmds.click(moveLocation);
            } else {
                log.severe("Failed to click moveGeneral button");
            }
        } else {
            log.severe("Open menu failed");
        }
    }

    public void gotoPosOneAndZoomOut() {
        islandCmds.type("0");
        zoomOut();
    }

    public boolean openGeneralMenu(GeneralType general, String generalName) {
        boolean rv = false;
        if (!starMenu.openStarMenu(generalName)) {
            return rv;
        }
        Match match = islandCmds.find(general.getPattern(), starMenu.getMenuRegion());
        if (match != null) {
            islandCmds.sleep();
            if (match.click() == 1) {
                rv = true;
            } else {
                log.severe("Click was not successfull");
            }
        } else {
            log.severe("General not found");
        }
        return rv;
    }

    public void zoomOut() {
        islandCmds.type("-");
        //        islandCmds.sleep();
        //        islandCmds.stepType("-");
        //        islandCmds.sleep();
        //        islandCmds.stepType("-");
        //        islandCmds.sleep();
    }

    private boolean unsetAllUnits() {
        return generalMenu.unsetAllUnits();
    }

    public void prepareStarMenu(StarMenuFilter filter) {
        dsoService.prepareStarMenu(filter);
    }
}
