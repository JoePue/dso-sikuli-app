package de.puettner.sikuli.dso.commands.ui;

import de.puettner.sikuli.dso.InstanceBuilder;
import de.puettner.sikuli.dso.adv.GeneralMenu;
import de.puettner.sikuli.dso.commands.os.WindowsPlatform;
import lombok.extern.java.Log;
import org.sikuli.basics.Debug;
import org.sikuli.script.App;
import org.sikuli.script.ImagePath;
import org.sikuli.script.Region;

import static de.puettner.sikuli.dso.commands.os.WindowsPlatform.CHROME_EXE;
import static de.puettner.sikuli.dso.commands.ui.IslandButtons.Avatar;

@Log
public class MenuBuilder {

    private static IslandCommands islandCmds;
    private static MenuBuilder menuBuilder;
    private static BuildMenu buildMenu;
    private static BuildQueueMenu buildQueueMenu;
    private static StarMenu starMenu;
    private static BookbinderMenu bookbinderMenu;
    private static QuestBookMenu questBookCmds;
    private static MessageBoxMenu messageBoxMenu;
    private static GeneralMenu generalMenu;

    private final Region appRegion;

    private MenuBuilder(Region chromeAppRegion) {
        this.appRegion = chromeAppRegion;
        this.appRegion.x = chromeAppRegion.x + 10; // without scrollbar
        this.appRegion.y = chromeAppRegion.y + 115; // without chrome menu bar
        this.appRegion.w = chromeAppRegion.w - 40;
        this.appRegion.h = chromeAppRegion.h - 115;
    }

    public static MenuBuilder build() {
        if (menuBuilder == null) {
            WindowsPlatform platformCmds = new WindowsPlatform(InstanceBuilder.buildAppEnvironment());
            menuBuilder = new MenuBuilder(platformCmds.getDsoBrowserDimension().getRegion());
        }
        return menuBuilder;
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

    public BuildMenu buildBuildMenuCommands() {
        if (buildMenu == null) {
            buildMenu = new BuildMenu(appRegion, buildIslandCommand());
        }
        return buildMenu;
    }

    public IslandCommands buildIslandCommand() {
        if (islandCmds == null) {
            String path = "../dso-sikuli-app.sikuli";
            boolean result = ImagePath.add(path);
            if (!result) {
                throw new IllegalArgumentException("invalid path: " + path);
            }
            path = "./dso-sikuli-app.sikuli";
            result = ImagePath.add(path); // from dist-folder
            if (!result) {
                throw new IllegalArgumentException("invalid path: " + path);
            }
            log.fine("Avatar-getFilename: " + Avatar.getPattern().getFilename());
            log.fine("Avatar-getImage: " + Avatar.getPattern().getImage().get());
            if (Avatar.getPattern().getImage().get() == null) {
                throw new IllegalStateException("Avatar image must exists");
            }
            Debug.setDebugLevel(0);
            islandCmds = new IslandCommands(new App(CHROME_EXE), appRegion);
        }
        return islandCmds;
    }

    public BuildQueueMenu buildBuildQueueMenuCommands() {
        if (buildQueueMenu == null) {
            buildQueueMenu = new BuildQueueMenu(calculateBuildQueueRegion(), buildIslandCommand());
        }
        return buildQueueMenu;
    }

    public Region calculateBuildQueueRegion() {
        final int menuWidth = 150, menuHeight = 400;
        Region region = new Region(0, 0, 0, 0);
        region.x = appRegion.x + appRegion.w - menuWidth;
        region.y = appRegion.y;
        region.w = menuWidth;
        region.h = menuHeight;
        return region;
    }

    public StarMenu buildStarMenuCommands() {
        if (starMenu == null) {
            starMenu = new StarMenu(calculateStarMenuRegion(), buildIslandCommand());
        }
        return starMenu;
    }

    public Region calculateStarMenuRegion() {
        final int menuWidth = 650, menuHeight = 600;
        Region region = new Region(0, 0, 0, 0);
        region.x = appRegion.x + (appRegion.w / 2) - (menuWidth / 2);
        region.y = appRegion.y + (appRegion.h / 2) - (menuHeight / 2) + 100;
        region.w = menuWidth;
        region.h = menuHeight;
        return region;
    }

    public BookbinderMenu buildBookbinderMenuCommands() {
        if (bookbinderMenu == null) {
            bookbinderMenu = new BookbinderMenu(appRegion, buildIslandCommand());
        }
        return bookbinderMenu;
    }

    public QuestBookMenu buildQuestBookMenuCommands() {
        if (questBookCmds == null) {
            questBookCmds = new QuestBookMenu(appRegion, buildIslandCommand());
        }
        return questBookCmds;
    }

    public MessageBoxMenu buildMessageBoxMenu() {
        if (messageBoxMenu == null) {
            Region region = calculateStarMenuRegion();
            region.y = region.y - 150;
            region.x = region.x - 50;
            region.w = region.w + 100;
            messageBoxMenu = new MessageBoxMenu(region, buildIslandCommand());
        }
        return messageBoxMenu;
    }

    public GeneralMenu buildGeneralMenu() {
        if (generalMenu == null) {
            Region region = calculateStarMenuRegion();
            region.y = region.y - 150;
            region.x = region.x - 50;
            region.w = region.w + 100;
            generalMenu = new GeneralMenu(region, buildIslandCommand());
        }
        return generalMenu;
    }
}
