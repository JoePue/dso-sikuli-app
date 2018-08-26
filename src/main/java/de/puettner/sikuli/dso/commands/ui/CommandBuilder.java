package de.puettner.sikuli.dso.commands.ui;

import de.puettner.sikuli.dso.commands.os.WindowsOS;
import org.sikuli.script.App;
import org.sikuli.script.ImagePath;
import org.sikuli.script.Region;

import static de.puettner.sikuli.dso.commands.os.WindowsOS.CHROME_EXE;

public class CommandBuilder {

    private static CommandBuilder cmdBuilder;
    private static IslandCommands islandCmds;
    private static BuildMenuCommands buildMenuCommands;
    private static BuildQueueMenuCommands buildQueueMenuCommands;
    private static StarMenuCommands starMenuCommands;
    private static BookbinderMenuCommands bookbinderMenuCommands;
    private static QuestBookMenuCommands questBookCmds;

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
            WindowsOS osCommands = new WindowsOS();
            cmdBuilder = new CommandBuilder(osCommands.getDsoBrowserDimension().getRegion());
        }
        return cmdBuilder;
    }

    public BuildMenuCommands buildBuildMenuCommands() {
        if (buildMenuCommands == null) {
            buildMenuCommands = new BuildMenuCommands(dsoAppRegion, buildIslandCommand());
        }
        return buildMenuCommands;
    }

    public IslandCommands buildIslandCommand() {
        if (islandCmds == null) {
            ImagePath.add("../dso_1.sikuli");
            islandCmds = new IslandCommands(new App(CHROME_EXE), dsoAppRegion, this.calculateGeologicSearchRegion());
        }
        return islandCmds;
    }

    public Region calculateGeologicSearchRegion() {
        final int menuWidth = dsoAppRegion.w - 50, menuHeight = dsoAppRegion.h - 200;
        Region region = new Region(0, 0, 0, 0);
        region.x = dsoAppRegion.x + (dsoAppRegion.w / 2) - (menuWidth / 2);
        region.y = dsoAppRegion.y + (dsoAppRegion.h / 2) - (menuHeight / 2) - 50;
        region.w = menuWidth;
        region.h = menuHeight;
        return region;
    }

    public BuildQueueMenuCommands buildBuildQueueMenuCommands() {
        if (buildQueueMenuCommands == null) {
            buildQueueMenuCommands = new BuildQueueMenuCommands(calculateBuildQueueRegion(), buildIslandCommand());
        }
        return buildQueueMenuCommands;
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
            starMenuCommands = new StarMenuCommands(calculateStarMenuRegion(), buildIslandCommand());
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
            bookbinderMenuCommands = new BookbinderMenuCommands(dsoAppRegion, buildIslandCommand());
        }
        return bookbinderMenuCommands;
    }

    public QuestBookMenuCommands buildQuestBookMenuCommands() {
        if (questBookCmds == null) {
            questBookCmds = new QuestBookMenuCommands(dsoAppRegion, buildIslandCommand());
        }
        return questBookCmds;
    }

}
