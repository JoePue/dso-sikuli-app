package de.puettner.sikuli.dso.commands.ui;

import lombok.extern.java.Log;
import org.sikuli.script.Match;
import org.sikuli.script.Region;

@Log
public class MessageBoxMenu extends DsoMenu {

    protected MessageBoxMenu(Region menuRegion, IslandCommands islandCmds) {
        super(menuRegion, islandCmds);
    }

    /**
     * Assumes a open message box. Abholung der Rohstoffe aus der Nachrichtenbox.
     */
    public int fetchRewardMessages() {
        log.info("fetchRewardMessages()");
        int i = 0, loopLimit = 100;
        Match match = null;
        do {
            match = islandCmds.find(MessageBoxButton.ExplorerTreasureRewardMessageIcon.pattern, menuRegion);
            if (match == null) {
                match = islandCmds.find(MessageBoxButton.ExplorerCardFragmentsRewardMessageIcon.pattern, menuRegion);
            }
            if (match != null) {
                ++i;
                match.hover();
                if (match.click() > 0) {
                    islandCmds.sleepX(3);
                    if (clickOkBuildingButton()) {
                        islandCmds.sleepX(2);
                    } else {
                        log.severe("clickOkBuildingButton failed");
                    }
                } else {
                    log.severe("click failed");
                }
                if (i > loopLimit) {
                    log.info("loop limit reached");
                }
            } else {
                log.info("match is null, everything was collected");
            }
        } while (match != null);
        log.info("everything collected.");
        return i;
    }

    private boolean clickOkBuildingButton() {
        return islandCmds.clickOkButton(MessageBoxButton.OK_BUILDING);
    }

    public void closeMenu() {
        log.info("closeMenu()");
        islandCmds.typeESC();
        islandCmds.sleep();
    }
}
