package de.puettner.sikuli.dso.adv;

import de.puettner.sikuli.dso.AppEnvironment;
import de.puettner.sikuli.dso.DSOService;
import de.puettner.sikuli.dso.commands.ui.IslandCommands;
import de.puettner.sikuli.dso.commands.ui.StarMenu;
import de.puettner.sikuli.dso.commands.ui.StarMenuFilter;
import lombok.extern.java.Log;
import org.sikuli.script.Region;

/**
 * brave tailor = tapferes Schneiderlein
 */
@Log
public class BraveTailorAdv extends Adventure {

    /**
     * C'tor
     */
    protected BraveTailorAdv(IslandCommands islandCmds, StarMenu starMenu, DSOService dsoService, AppEnvironment appEnvironment, Region
            dsoAppRegion) {
        super(islandCmds, starMenu, dsoService, appEnvironment, new BraveTailorAdventureRouter(islandCmds, dsoAppRegion),
                "brave-tailor-adventure.json");
    }

    @Override
    protected NavigationPoint getFirstNavigationPoint() {
        return BraveTailorNavPoints.NP_1;
    }

    private void prepareStarMenu() {
        super.prepareStarMenu(StarMenuFilter.GeneralsFilterString);
    }

}
