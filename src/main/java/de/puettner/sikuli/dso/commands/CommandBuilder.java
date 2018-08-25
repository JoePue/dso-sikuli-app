package de.puettner.sikuli.dso.commands;

import org.sikuli.script.App;
import org.sikuli.script.ImagePath;
import org.sikuli.script.Region;

import static de.puettner.sikuli.dso.Constants.CHROME_EXE;

public abstract class CommandBuilder {

    private static final Region DSO_APP_REGION = new Region(10, 115, 1290 - 10, 1047 - 115);
    private static SikuliCommands sikuliCommands;
    private static BuildMenuCommands buildMenuCommands;
    private static StarMenuCommands starMenuCommands;
    private static BookbinderMenuCommands bookbinderMenuCommands;

    public static BuildMenuCommands buildBuildMenuCommands() {
        if (buildMenuCommands == null) {
            buildMenuCommands = new BuildMenuCommands(DSO_APP_REGION, buildSikuliCommand());
        }
        return buildMenuCommands;
    }

    public static SikuliCommands buildSikuliCommand() {
        if (sikuliCommands == null) {
            ImagePath.add("../dso_1.sikuli");
            // setROI(10, 115, 1290 - 10, 1047 - 115)
            sikuliCommands = new SikuliCommands(new App(CHROME_EXE), DSO_APP_REGION, calculateStarMenuRegion(), calculateBuildListRegion());
        }
        return sikuliCommands;
    }

    public static Region calculateStarMenuRegion() {
        Region region = new Region(0, 0, 0, 0);
        region.w = 650;
        region.h = 600;
        region.x = (int) (0.3 * DSO_APP_REGION.w);
        region.y = (int) (0.45 * DSO_APP_REGION.h);
        return region;
    }

    public static Region calculateBuildListRegion() {
        Region region = new Region(0, 0, 0, 0);
        region.w = 50;
        region.h = 100;
        region.x = (int) (DSO_APP_REGION.w - region.w);
        region.y = DSO_APP_REGION.y;
        return region;
    }

    public static StarMenuCommands buildStarMenuCommands() {
        if (starMenuCommands == null) {
            starMenuCommands = new StarMenuCommands(calculateStarMenuRegion(), buildSikuliCommand());
        }
        return starMenuCommands;
    }

    public static BookbinderMenuCommands buildBookbinderMenuCommands() {
        if (bookbinderMenuCommands == null) {
            bookbinderMenuCommands = new BookbinderMenuCommands(DSO_APP_REGION, buildSikuliCommand());
        }
        return bookbinderMenuCommands;
    }
}
