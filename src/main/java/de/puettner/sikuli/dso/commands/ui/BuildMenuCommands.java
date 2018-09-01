package de.puettner.sikuli.dso.commands.ui;

import lombok.extern.java.Log;
import org.sikuli.script.Match;
import org.sikuli.script.Region;

@Log
public class BuildMenuCommands extends MenuCommands {

    public BuildMenuCommands(Region menuRegion, IslandCommands islandCmds) {
        super(menuRegion, islandCmds);
    }

    public void prepareBuildMenu(BuildMenuButtons buildingType) {
        log.info("prepareBuildMenu");
        this.clickButton(buildingType);
        islandCmds.typeESC();
    }

    public void clickButton(BuildMenuButtons entry) {
        islandCmds.clickIfExists(entry.pattern, menuRegion);
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
        return islandCmds.exists(BuildMenuButtons.RaisedBuildingMenuDisabled.pattern);
    }
}
