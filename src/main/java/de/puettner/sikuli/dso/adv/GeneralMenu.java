package de.puettner.sikuli.dso.adv;

import de.puettner.sikuli.dso.commands.ui.DsoMenu;
import de.puettner.sikuli.dso.commands.ui.IslandCommands;
import lombok.extern.java.Log;
import org.sikuli.script.*;

@Log
public class GeneralMenu extends DsoMenu {

    public GeneralMenu(Region menuRegion, IslandCommands islandCmds) {
        super(menuRegion, islandCmds);
    }

    public boolean setupAttackUnits(AttackUnit[] units) {
        log.info("setupAttackUnits()");
        boolean rv = true;
        islandCmds.parkMouse();
        Match match = islandCmds.find(GeneralMenuButton.RookieIcon.pattern, menuRegion);
        if (match != null) {
            if (unsetAllUnits()) {
                islandCmds.sleepX(1);
            }
            for (AttackUnit unit : units) {
                try {
                    AttackUnitType type = unit.getType();
                    match.click(new Location(match.x + type.xOffset, match.y + type.yOffset));
                    islandCmds.type("a", Key.CTRL);
                    islandCmds.sleep();
                    islandCmds.paste(unit.getQuantity());
                } catch (FindFailed findFailed) {
                    rv = false;
                    throw new RuntimeException(findFailed);
                }
            }
        } else {
            log.severe("Rookie Icon not found.");
            rv = false;
        }
        if (!islandCmds.clickBigOkButtonAndWait()) {
            rv = false;
        }
        // todo impl setup check
        // islandCmds.sleepX(20); // Das Speichern der Aufstellung kann lange dauern.
        return rv;
    }

    protected boolean unsetAllUnits() {
        log.info("unsetAllUnits");
        boolean rv = clickIfExists(GeneralMenuButton.ReleaseUnits.pattern);
        if (rv) {
            islandCmds.sleepX(2);
        }
        return rv;
    }

    public boolean clickAttackBtn() {
        log.info("clickAttackBtn");
        return clickIfExists(GeneralMenuButton.Attack.pattern);
    }

    public boolean clickMoveBtn() {
        log.info("clickMoveBtn");
        boolean rv = clickIfExists(GeneralMenuButton.Move.pattern);
        if (rv) {
            islandCmds.sleepX(5);
        }
        return rv;
    }

    public boolean putBackToStarMenu() {
        log.info("putBackToStarMenu");
        boolean rv = clickIfExists(GeneralMenuButton.PutBackToStarMenu.pattern);
        if (rv) {
            islandCmds.sleep();
            rv = islandCmds.clickSmallOkButton();
        }
        return rv;
    }
}
