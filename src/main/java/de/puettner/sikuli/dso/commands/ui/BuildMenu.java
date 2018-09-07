package de.puettner.sikuli.dso.commands.ui;

import lombok.extern.java.Log;
import org.sikuli.script.Match;
import org.sikuli.script.Region;

@Log
public class BuildMenu extends DsoMenu {

    public BuildMenu(Region menuRegion, IslandCommands islandCmds) {
        super(menuRegion, islandCmds);
    }

    public void prepareBuildMenu(MenuButton buildingType) {
        log.info("prepareBuildMenu");
        this.clickButton(buildingType);
        islandCmds.typeESC();
    }

    public void clickButton(MenuButton entry) {
        islandCmds.clickIfExists(entry.getPattern(), menuRegion);
    }

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
