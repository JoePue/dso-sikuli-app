package de.puettner.sikuli.dso.adv;

import de.puettner.sikuli.dso.commands.os.WindowsPlatform;
import de.puettner.sikuli.dso.commands.ui.IslandCommands;
import org.sikuli.basics.Debug;
import org.sikuli.script.App;
import org.sikuli.script.ImagePath;
import org.sikuli.script.Region;

import static de.puettner.sikuli.dso.commands.os.WindowsPlatform.CHROME_EXE;
import static de.puettner.sikuli.dso.commands.ui.MenuBuilder.calculateGeologicSearchRegion;

public class AdventureBuilder {

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
            braveTailorAdv = new BraveTailorAdv(buildIslandCommand());
        }
        return braveTailorAdv;
    }

    public IslandCommands buildIslandCommand() {
        if (islandCmds == null) {
            ImagePath.add("../dso_1.sikuli");
            Debug.setDebugLevel(5);
            islandCmds = new IslandCommands(new App(CHROME_EXE), dsoAppRegion, calculateGeologicSearchRegion(dsoAppRegion));
        }
        return islandCmds;
    }

}
