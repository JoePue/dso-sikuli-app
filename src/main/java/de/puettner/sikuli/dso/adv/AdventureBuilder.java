package de.puettner.sikuli.dso.adv;

import de.puettner.sikuli.dso.commands.os.WindowsPlatform;
import de.puettner.sikuli.dso.commands.ui.IslandCommands;
import de.puettner.sikuli.dso.commands.ui.MenuBuilder;
import org.sikuli.script.Region;

public class AdventureBuilder {

    private MenuBuilder menuBuilder = MenuBuilder.build();
    private static AdventureBuilder advBuilder;
    private static IslandCommands islandCmds;
    private static BraveTailorAdv braveTailorAdv;

    private final Region dsoAppRegion;

    private AdventureBuilder(Region chromeAppRegion) {
        this.dsoAppRegion = chromeAppRegion;
        this.dsoAppRegion.x = chromeAppRegion.x + 10;
        this.dsoAppRegion.y = chromeAppRegion.y + 115;
        this.dsoAppRegion.w = chromeAppRegion.w - 40;
        this.dsoAppRegion.h = chromeAppRegion.h - 115;
    }

    public static AdventureBuilder build() {
        if (advBuilder == null) {
            WindowsPlatform platformCmds = new WindowsPlatform();
            advBuilder = new AdventureBuilder(platformCmds.getDsoBrowserDimension().getRegion());
        }
        return advBuilder;
    }

    public BraveTailorAdv buildTapferesSchneiderleinAT() {
        if (braveTailorAdv == null) {
            braveTailorAdv = new BraveTailorAdv(menuBuilder.buildIslandCommand(), menuBuilder.buildStarMenuCommands());
        }
        return braveTailorAdv;
    }

}
