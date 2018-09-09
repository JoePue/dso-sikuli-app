package de.puettner.sikuli.dso.adv;

import de.puettner.sikuli.dso.commands.ui.IslandCommands;
import de.puettner.sikuli.dso.commands.ui.MenuBuilder;
import de.puettner.sikuli.dso.commands.ui.StarMenu;
import lombok.extern.java.Log;
import org.sikuli.script.Match;
import org.sikuli.script.Region;

import java.util.Optional;

import static de.puettner.sikuli.dso.adv.AttackUnitType.*;

@Log
public class Adventure {

    protected IslandCommands islandCmds;
    private final StarMenu starMenu;
    protected GeneralMenu generalMenu;
    protected final Region region;

    protected Adventure(IslandCommands islandCmds, StarMenu starMenu) {
        this.islandCmds = islandCmds;
        this.starMenu = starMenu;
        this.generalMenu = MenuBuilder.build().buildGeneralMenu();
        this.region = islandCmds.getIslandRegion();
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
                    log.severe("Cancel must not exists.");
                    return false;
                }
            }
        } else {
            log.info("camp not found. camp: " + camp);
        }
        return rv;
    }

    private boolean campExists(AttackCamp camp) {
        Match match = islandCmds.find(camp.getPattern(), region);
        if (match == null) {
            return false;
        }
        return true;
    }

    /**
     * This method assumes a open general menu.
     */
    protected boolean attack(AttackCamp camp) {
        boolean rv = false;
        if (generalMenu.clickAttackBtn()) {
            if (camp.getDragNDrop() != null) {
                islandCmds.dragDrop(camp.getDragNDrop().x, camp.getDragNDrop().y);
                islandCmds.sleep();
            }
            Match match = islandCmds.find(camp.getPattern(), region);
            if (match != null) {
                match.hover();
                islandCmds.sleepX(5);
                match.doubleClick();
                islandCmds.sleepX(10);
                // islandCmds.click(match);
            } else {
                log.severe("Camp not found: " + camp);
            }
            rv = true;
        } else {
            log.severe("Missing Attack Btn");
        }

        return false;
    }

    private boolean setupGeneral(AttackUnit[] units) {
        return generalMenu.setupAttackUnits(units);
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

    public void zoomOut() {
        islandCmds.type("0");
        islandCmds.sleep();
        islandCmds.type("-");
        islandCmds.sleep();
        islandCmds.type("-");
        islandCmds.sleep();
        islandCmds.type("-");
        islandCmds.sleep();
    }

    public void gotoPosOneAndZoomOut() {
        islandCmds.type("0");
        zoomOut();
    }
}
