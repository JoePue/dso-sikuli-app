package de.puettner.sikuli.dso.commands.ui;

import de.puettner.sikuli.dso.commands.os.WindowsPlatformHelper;
import org.sikuli.script.App;
import org.sikuli.script.ImagePath;
import org.sikuli.script.Region;

import static de.puettner.sikuli.dso.commands.os.WindowsPlatformHelper.CHROME_EXE;

public class CommandBuilder {

    private static CommandBuilder cmdBuilder;
    private static SikuliCommands sikuliCmds;
    private static BuildMenuCommands buildMenuCommands;
    private static StarMenuCommands starMenuCommands;
    private static BookbinderMenuCommands bookbinderMenuCommands;
    private final Region dsoAppRegion;

    private CommandBuilder(Region chromeAppRegion) {
        this.dsoAppRegion = chromeAppRegion;
        this.dsoAppRegion.x = chromeAppRegion.x + 10;
        this.dsoAppRegion.y = chromeAppRegion.y + 115;
        this.dsoAppRegion.w = chromeAppRegion.w - 40;
        this.dsoAppRegion.h = chromeAppRegion.h - 115;
    }

    public static CommandBuilder build() {
        if (cmdBuilder == null) {
            cmdBuilder = new CommandBuilder(WindowsPlatformHelper.getChromeDimension().getRegion());
        }
        return cmdBuilder;
    }

    public BuildMenuCommands buildBuildMenuCommands() {
        if (buildMenuCommands == null) {
            buildMenuCommands = new BuildMenuCommands(dsoAppRegion, buildSikuliCommand());
        }
        return buildMenuCommands;
    }

    public SikuliCommands buildSikuliCommand() {
        if (sikuliCmds == null) {
            ImagePath.add("../dso_1.sikuli");
            sikuliCmds = new SikuliCommands(new App(CHROME_EXE), dsoAppRegion);
        }
        return sikuliCmds;
    }

    public Region calculateBuildQueueRegion() {
        final int menuWidth = 150, menuHeight = 400;
        Region region = new Region(0, 0, 0, 0);
        region.x = dsoAppRegion.x + dsoAppRegion.w - menuWidth;
        region.y = dsoAppRegion.y;
        region.w = menuWidth;
        region.h = menuHeight;
        return region;
    }

    public StarMenuCommands buildStarMenuCommands() {
        if (starMenuCommands == null) {
            starMenuCommands = new StarMenuCommands(calculateStarMenuRegion(), buildSikuliCommand());
        }
        return starMenuCommands;
    }

    public Region calculateStarMenuRegion() {
        final int menuWidth = 650, menuHeight = 600;
        Region region = new Region(0, 0, 0, 0);
        region.x = dsoAppRegion.x + (dsoAppRegion.w / 2) - (menuWidth / 2);
        region.y = dsoAppRegion.y + (dsoAppRegion.h / 2) - (menuHeight / 2) + 100;
        region.w = menuWidth;
        region.h = menuHeight;
        return region;
    }

    public BookbinderMenuCommands buildBookbinderMenuCommands() {
        if (bookbinderMenuCommands == null) {
            bookbinderMenuCommands = new BookbinderMenuCommands(dsoAppRegion, buildSikuliCommand());
        }
        return bookbinderMenuCommands;
    }
}
