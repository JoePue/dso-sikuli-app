package de.puettner.sikuli.dso.adv;

import de.puettner.sikuli.dso.AppEnvironment;
import de.puettner.sikuli.dso.DSOServiceBuilder;
import de.puettner.sikuli.dso.commands.os.WindowsPlatform;
import de.puettner.sikuli.dso.commands.ui.MenuBuilder;
import org.sikuli.script.Region;

public class AdventureBuilder {

    private static AdventureBuilder advBuilder;
    private static BraveTailorAdv braveTailorAdv;
    private final Region dsoAppRegion;
    private final AppEnvironment appEnvironment;
    private MenuBuilder menuBuilder = MenuBuilder.build();

    private AdventureBuilder(Region chromeAppRegion, AppEnvironment appEnvironment) {
        this.dsoAppRegion = chromeAppRegion;
        this.appEnvironment = appEnvironment;
        this.dsoAppRegion.x = chromeAppRegion.x + 10;
        this.dsoAppRegion.y = chromeAppRegion.y + 115;
        this.dsoAppRegion.w = chromeAppRegion.w - 40;
        this.dsoAppRegion.h = chromeAppRegion.h - 115;
    }

    public static AdventureBuilder build() {
        if (advBuilder == null) {
            WindowsPlatform platformCmds = new WindowsPlatform();
            advBuilder = new AdventureBuilder(platformCmds.getDsoBrowserDimension().getRegion(), AppEnvironment.getInstance());
        }
        return advBuilder;
    }

    public BraveTailorAdv buildBraveTailorAdv() {
        if (braveTailorAdv == null) {
            braveTailorAdv = new BraveTailorAdv(menuBuilder.buildIslandCommand(), menuBuilder.buildStarMenuCommands(), DSOServiceBuilder
                    .build(), appEnvironment, dsoAppRegion);
        }
        return braveTailorAdv;
    }

}
