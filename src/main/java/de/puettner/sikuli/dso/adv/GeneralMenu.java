package de.puettner.sikuli.dso.adv;

import de.puettner.sikuli.dso.commands.ui.DsoMenu;
import de.puettner.sikuli.dso.commands.ui.IslandCommands;
import lombok.extern.java.Log;
import org.sikuli.script.*;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;

@Log
public class GeneralMenu extends DsoMenu {

    Toolkit toolkit = Toolkit.getDefaultToolkit();
    Clipboard clipboard = toolkit.getSystemClipboard();

    public GeneralMenu(Region menuRegion, IslandCommands islandCmds) {
        super(menuRegion, islandCmds);
    }

    public boolean setupAttackUnits(AttackUnit[] units) {
        log.info("setupAttackUnits()");
        boolean rv = true;
        islandCmds.parkMouse();
        Match match = islandCmds.find(GeneralMenuButton.RookieIcon.pattern, menuRegion);
        if (match != null) {
            if (clickUnsetUnitButton()) {
                islandCmds.sleepX(1);
            }
            for (AttackUnit unit : units) {
                try {
                    AttackUnitType type = unit.getType();
                    match.click(new Location(match.x + type.xOffset, match.y + type.yOffset));
                    islandCmds.type("a", Key.CTRL);
                    islandCmds.sleep();
                    islandCmds.paste(unit.getQuantity());
                    checkQuantity(unit.getQuantity());
                } catch (FindFailed findFailed) {
                    throw new RuntimeException(findFailed);
                }
            }
        } else {
            log.severe("Rookie Icon not found.");
            rv = false;
        }
        if (!islandCmds.clickBigOkButtonAndWait()) {
            if (!islandCmds.clickBigOkButtonAndWait()) {
                rv = false;
            }
        }
        return rv;
    }

    /**
     * Expects an open general menu.
     */
    protected boolean clickUnsetUnitButton() {
        log.info("clickUnsetUnitButton");
        boolean rv = clickIfExists(GeneralMenuButton.ReleaseUnits.pattern);
        if (rv) {
            islandCmds.sleepX(2);
        }
        return rv;
    }

    private void checkQuantity(int expectedQuantity) {
        islandCmds.type("a", Key.CTRL);
        islandCmds.sleep();
        islandCmds.type("c", Key.CTRL);
        String actual = null;
        try {
            actual = (String) clipboard.getData(DataFlavor.stringFlavor);
            if (actual == null || !actual.equals("" + expectedQuantity)) {
                throw new IllegalStateException(String.format("Unit quantity mismatch. expectedQuantity: %s, actual: %s",
                        expectedQuantity, actual));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Expects an open general menu.
     */
    public boolean unsetUnits() {
        log.info("unsetUnits");
        boolean rv = clickUnsetUnitButton();
        if (rv) {
            islandCmds.sleep();
            rv = islandCmds.clickBigOkButton();
            this.closeMenu();
        }
        return rv;
    }

    /**
     * Expects an open general menu.
     */
    public boolean clickAttackBtn() {
        log.info("clickAttackBtn");
        return clickIfExists(GeneralMenuButton.Attack.pattern);
    }

    /**
     * Expects an open general menu.
     */
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
