package de.puettner.sikuli.dso.adv;

import de.puettner.sikuli.dso.DSOService;
import de.puettner.sikuli.dso.commands.ui.IslandCommands;
import de.puettner.sikuli.dso.commands.ui.MenuBuilder;
import de.puettner.sikuli.dso.commands.ui.StarMenu;
import de.puettner.sikuli.dso.commands.ui.StarMenuFilter;
import lombok.extern.java.Log;
import org.sikuli.script.Location;
import org.sikuli.script.Match;
import org.sikuli.script.Region;

import java.util.Optional;

import static de.puettner.sikuli.dso.adv.AttackUnitType.*;

@Log
public class Adventure {

    protected final Region region;
    private final StarMenu starMenu;
    private final DSOService dsoService;
    protected IslandCommands islandCmds;
    protected GeneralMenu generalMenu;

    protected Adventure(IslandCommands islandCmds, StarMenu starMenu, DSOService dsoService) {
        this.islandCmds = islandCmds;
        this.starMenu = starMenu;
        this.generalMenu = MenuBuilder.build().buildGeneralMenu();
        this.region = islandCmds.getIslandRegion();
        this.dsoService = dsoService;
    }

    public static AttackUnit Rek(int i) {
        return AttackUnit.builder().type(Rek).quantity(i).build();
    }

    public static AttackUnit Bos(int i) {
        return AttackUnit.builder().type(Bos).quantity(i).build();
    }

    public static AttackUnit Cav(int i) {
        return AttackUnit.builder().type(Cav).quantity(i).build();
    }

    public static AttackUnit Lnb(int i) {
        return AttackUnit.builder().type(Lnb).quantity(i).build();
    }

    public static AttackUnit Sol(int i) {
        return AttackUnit.builder().type(Sol).quantity(i).build();
    }

    public static AttackUnit Arm(int i) {
        return AttackUnit.builder().type(Arm).quantity(i).build();
    }

    public static AttackUnit Kan(int i) {
        return AttackUnit.builder().type(Kan).quantity(i).build();
    }

    protected boolean attack(AttackCamp camp, GeneralType general, AttackUnit... units) {
        boolean rv = false;
        gotoPosOneAndZoomOut();
        if (campExists(camp)) {
            log.info("Camp found: " + camp);
            if (openGeneralMenu(general)) {
                setupGeneral(units);
                if (attack(camp)) {

                }
                if (islandCmds.clickBuildCancelButton()) {
                    log.severe("Cancel-Button must not exists.");
                    return false;
                }
            }
        } else {
            log.info("camp not found. camp: " + camp);
        }
        return rv;
    }

    public void gotoPosOneAndZoomOut() {
        islandCmds.type("0");
        zoomOut();
    }

    private boolean campExists(AttackCamp camp) {
        if (camp.getDragNDrop() != null) {
            islandCmds.dragDrop(camp.getDragNDrop());
        }
        Match match = islandCmds.find(camp.getPattern(), region);
        if (match == null) {
            return false;
        }
        match.hover();
        return true;
    }

    public boolean openGeneralMenu(GeneralType general) {
        boolean rv = false;
        starMenu.openStarMenu(Optional.empty());
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

    private boolean setupGeneral(AttackUnit[] units) {
        return generalMenu.setupAttackUnits(units);
    }

    /**
     * This method assumes a open general menu.
     */
    protected boolean attack(AttackCamp camp) {
        boolean rv = false;
        if (generalMenu.clickAttackBtn()) {
            if (camp.getDragNDrop() != null) {
                this.gotoPosOneAndZoomOut();
                islandCmds.dragDrop(camp.getDragNDrop());
                islandCmds.sleepX(5);
            }
            Match match = islandCmds.find(camp.getPattern(), region);
            if (match != null) {
                match.hover();
                islandCmds.sleepX(5);
                match.doubleClick(); // Angriff starten
                islandCmds.sleepX(2);
            } else {
                log.severe("Camp not found: " + camp);
            }
            rv = true;
        } else {
            log.severe("Missing Attack Btn");
        }

        return false;
    }

    public void zoomOut() {
        islandCmds.type("0");
        islandCmds.sleep();
        //        islandCmds.type("-");
        //        islandCmds.sleep();
        //        islandCmds.type("-");
        //        islandCmds.sleep();
        //        islandCmds.type("-");
        //        islandCmds.sleep();
    }

    void moveGeneral(GeneralType general, BraveTailorAttackCamp movePoint, Location moveOffset) {
        gotoPosOneAndZoomOut();
        if (openGeneralMenu(general)) {
            islandCmds.sleepX(5);
            // unsetAllUnits();
            if (generalMenu.clickMoveBtn()) {
                gotoPosOneAndZoomOut();

                islandCmds.dragDrop(movePoint.getDragNDrop());
                // Find ref point and place Gen
                Match match = islandCmds.find(movePoint.getPattern(), region);
                if (match == null) {
                    log.warning("Move point not found");
                    islandCmds.sleepX(10);
                }
                match = islandCmds.find(movePoint.getPattern(), region);
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

    private boolean unsetAllUnits() {
        return generalMenu.unsetAllUnits();
    }

    public void prepareStarMenu(StarMenuFilter filter) {
        dsoService.prepareStarMenu(filter);
    }
}
