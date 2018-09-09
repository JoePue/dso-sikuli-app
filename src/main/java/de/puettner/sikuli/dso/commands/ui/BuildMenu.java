package de.puettner.sikuli.dso.commands.ui;

import lombok.extern.java.Log;
import org.sikuli.script.Match;
import org.sikuli.script.Region;

@Log
public class BuildMenu extends DsoMenu {

    public BuildMenu(Region menuRegion, IslandCommands islandCmds) {
        super(menuRegion, islandCmds);
    }

    /**
     * Assumes a open build menu.
     */
    public boolean prepareBuildMenuTab(MenuButton buildingType) {
        log.info("prepareBuildMenuTab");
        boolean rv = this.clickButton(buildingType);
        if (!rv) {
            log.warning("... preparation was not successfull.");
        }
        islandCmds.typeESC();
        return rv;
    }

    public boolean clickButton(MenuButton entry) {
        return islandCmds.clickIfExists(entry.getPattern(), menuRegion);
    }

    /**
     * Assumes BuildMenu is open with the right tap.
     */
    public boolean buildMine(Match match, BuildMenuButtons mineButton) {
        log.info("buildMine");
        if (isRaisedBuildingMenuDisabled()) {
            log.info("Build-Menu is disabled");
            return false;
        }
        clickButton(mineButton);
        islandCmds.sleep();
        match.click();
        islandCmds.sleep();
        return true;
    }

    public boolean isRaisedBuildingMenuDisabled() {
        return islandCmds.exists(BuildMenuTabs.RaisedBuildingMenuDisabled.pattern);
    }

}
