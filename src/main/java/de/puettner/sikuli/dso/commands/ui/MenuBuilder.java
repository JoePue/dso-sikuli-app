package de.puettner.sikuli.dso.commands.ui;

import de.puettner.sikuli.dso.commands.os.WindowsPlatform;
import org.sikuli.basics.Debug;
import org.sikuli.script.App;
import org.sikuli.script.ImagePath;
import org.sikuli.script.Region;

import static de.puettner.sikuli.dso.commands.os.WindowsPlatform.CHROME_EXE;

public class MenuBuilder {

    private static IslandCommands islandCmds;
    private static MenuBuilder menuBuilder;
    private static BuildDsoMenu buildMenu;
    private static BuildQueueDsoMenu buildQueueMenu;
    private static StarDsoMenu starMenu;
    private static BookbinderDsoMenu bookbinderMenu;
    private static QuestBookDsoMenu questBookCmds;

    private final Region dsoAppRegion;

    private MenuBuilder(Region chromeAppRegion) {
        this.dsoAppRegion = chromeAppRegion;
        this.dsoAppRegion.x = chromeAppRegion.x + 10; // without scrollbar
        this.dsoAppRegion.y = chromeAppRegion.y + 115; // without chrome menu bar
        this.dsoAppRegion.w = chromeAppRegion.w - 40;
        this.dsoAppRegion.h = chromeAppRegion.h - 115;
    }

    public static MenuBuilder build() {
        if (menuBuilder == null) {
            WindowsPlatform platformCmds = new WindowsPlatform();
            menuBuilder = new MenuBuilder(platformCmds.getDsoBrowserDimension().getRegion());
        }
        return menuBuilder;
    }

    public BuildDsoMenu buildBuildMenuCommands() {
        if (buildMenu == null) {
            buildMenu = new BuildDsoMenu(dsoAppRegion, buildIslandCommand());
        }
        return buildMenu;
    }

    public IslandCommands buildIslandCommand() {
        if (islandCmds == null) {
            ImagePath.add("../dso_1.sikuli");
            Debug.setDebugLevel(5);
            islandCmds = new IslandCommands(new App(CHROME_EXE), dsoAppRegion, this.calculateGeologicSearchRegion(dsoAppRegion));
        }
        return islandCmds;
    }

    public static Region calculateGeologicSearchRegion(Region dsoAppRegion) {
        final int menuWidth = dsoAppRegion.w - 50, menuHeight = dsoAppRegion.h - 200;
        Region region = new Region(0, 0, 0, 0);
        region.x = dsoAppRegion.x + (dsoAppRegion.w / 2) - (menuWidth / 2);
        region.y = dsoAppRegion.y + (dsoAppRegion.h / 2) - (menuHeight / 2) - 50;
        region.w = menuWidth;
        region.h = menuHeight;
        return region;
    }

    public BuildQueueDsoMenu buildBuildQueueMenuCommands() {
        if (buildQueueMenu == null) {
            buildQueueMenu = new BuildQueueDsoMenu(calculateBuildQueueRegion(), buildIslandCommand());
        }
        return buildQueueMenu;
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

    public StarDsoMenu buildStarMenuCommands() {
        if (starMenu == null) {
            starMenu = new StarDsoMenu(calculateStarMenuRegion(), buildIslandCommand());
        }
        return starMenu;
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

    public BookbinderDsoMenu buildBookbinderMenuCommands() {
        if (bookbinderMenu == null) {
            bookbinderMenu = new BookbinderDsoMenu(dsoAppRegion, buildIslandCommand());
        }
        return bookbinderMenu;
    }

    public QuestBookDsoMenu buildQuestBookMenuCommands() {
        if (questBookCmds == null) {
            questBookCmds = new QuestBookDsoMenu(dsoAppRegion, buildIslandCommand());
        }
        return questBookCmds;
    }

}
