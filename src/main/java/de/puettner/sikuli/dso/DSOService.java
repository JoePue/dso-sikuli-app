package de.puettner.sikuli.dso;

import de.puettner.sikuli.dso.commands.os.WindowsPlatform;
import de.puettner.sikuli.dso.commands.ui.*;
import lombok.extern.java.Log;
import org.sikuli.script.Match;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.*;

import static de.puettner.sikuli.dso.commands.ui.SikuliCommands.pattern;

@Log
public class DSOService {

    private final WindowsPlatform winCommand;
    private final IslandCommands islandCmds;
    private final BuildMenu buildMenu;
    private final StarMenu starMenu;
    private final BookbinderMenu bookbinderMenu;
    private final BuildQueueMenu buildQueueMenu;
    private final QuestBookMenu questBookCmds;
    private final MessageBoxMenu messageboxMenu;


    DSOService(WindowsPlatform winCommand, IslandCommands islandCmds, BuildMenu buildMenu, StarMenu starMenu,
               BookbinderMenu bookbinderMenu, BuildQueueMenu buildQueueMenu, QuestBookMenu questBookCmds,
               MessageBoxMenu messageboxMenu) {
        this.winCommand = winCommand;
        this.islandCmds = islandCmds;
        this.buildMenu = buildMenu;
        this.starMenu = starMenu;
        this.bookbinderMenu = bookbinderMenu;
        this.buildQueueMenu = buildQueueMenu;
        this.questBookCmds = questBookCmds;
        this.messageboxMenu = messageboxMenu;
    }

    public void startDsoApp() {
        log.info("startDsoApp");
        if (!winCommand.isDsoBrowserRunning()) {
            throw new NotImplementedException();
        }
        focusBrowser();
        islandCmds.clickDsoTab();
        if (islandCmds.clickLetsPlayButton()) {
            this.closeWelcomeDialog();
            closeChat();
        } else {
            log.info("expected a running DSO app");
        }
    }

    public void focusBrowser() {
        log.info("focusBrowser");
        islandCmds.focusBrowser();
        islandCmds.sleep();
    }

    public void closeWelcomeDialog() {
        log.info("closeWelcomeDialog");
        int timeout = 300;
        int okButtonTimeout = 3;
        while (timeout > 0) {
            islandCmds.sleepX(20);
            timeout -= 1;
            if (islandCmds.existsAvatar()) {
                okButtonTimeout -= 1;
                if (okButtonTimeout < 0 || islandCmds.clickSmallOkButton()) {
                    islandCmds.sleepX(10);
                    timeout = 0;
                    islandCmds.sleepX(5);
                    // Login Bonus
                    if (!islandCmds.clickLoginBonusButton()) {
                        log.warning("LoginBonus not found");
                    }
                    this.visitAllSectors();
                }
            }
        }
        sleep();
    }

    boolean closeChat() {
        boolean rv = islandCmds.closeChat();
        if (!rv) {
            log.severe("closeChat failed");
        } else {
            islandCmds.parkMouse();
        }
        return rv;
    }

    public void visitAllSectors() {
        log.info("visitAllSectors()");
        for (Sector sector : Sector.values()) {
            this.goToSector(sector);
        }
        goToSector(Sector.S1);
    }

    void sleep() {
        islandCmds.sleep();
    }

    void goToSector(Sector sector) {
        log.info("goToSector() " + sector);
        int i = sector.index();
        if (i >= 0 && i <= 9) {
            islandCmds.type(i);
        } else {
            islandCmds.type(i - 9);
            if (i >= 10 && i <= 12) {
                islandCmds.dragDrop(300, 750);
            }
        }
        this.sleepX(2);
    }

    void sleepX(int ms) {
        islandCmds.sleepX(ms);
    }

    public boolean solveDailyQuest() {
        log.info("solveDailyQuest");
        boolean rv = false;
        if (islandCmds.openQuestBook()) {
            sleep();
            if (islandCmds.clickDailyQuestMenuItem()) {
                sleep();
                if (islandCmds.clickSmallOkButton()) {
                    sleep();
                    islandCmds.sleepX(40);
                    if (islandCmds.clickSmallOkButton()) {
                        rv = true;
                    }
                }
            }
        }
        if (!rv) {
            log.warning("DailyQuest NOT solved.");
        }
        islandCmds.typeESC();
        sleep();
        return rv;
    }

    public boolean solveGuildQuest() {
        log.info("solveGuildQuest");
        boolean rv = false;
        if (islandCmds.openQuestBook()) {
            sleep();
            if (questBookCmds.clickButton(QuestBookMenuButtons.GuildQuestMenuItem)) {
                sleep();
                if (islandCmds.clickSmallOkButton()) {
                    islandCmds.sleepX(20);
                    if (islandCmds.clickSmallOkButton()) {
                        islandCmds.sleepX(6);
                        rv = true;
                    }
                }
            }
        }
        islandCmds.typeESC();
        sleep();
        return rv;
    }

    public boolean findAllCollectables() {
        log.info("findAllCollectables");
        Sector[] sectors = Sector.valuesFromS1ToS9();
        for (Sector sector : sectors) {
            this.goToSector(sector);
            if (Sector.S3.equals(sector)) {
                islandCmds.dragDrop(0, 100);
            }
            islandCmds.parkMouse();
            IslandButtons[] collectableIcons = {/*IslandButtons.CollectableIconTwo,*/ IslandButtons.CollectableIconOne, IslandButtons
                    .CollectableIconThree};
            for (IslandButtons collectableIcon : collectableIcons) {
                Iterator<Match> iconIt = islandCmds.findAll(collectableIcon.pattern);
                if (iconIt != null) {
                    while (iconIt.hasNext()) {
                        Match match = iconIt.next();
                        log.info("Sammelgegenstand gefunden. " + match);
                        match.doubleClick();
                        islandCmds.parkMouse();
                        islandCmds.sleepX(8);
                        islandCmds.typeESC();
                    }
                } else {
                    log.info("No Collectables found");
                }
            }
        }
        return true;
    }

    @Deprecated
    public int launchAllHappyGeologics(MaterialType material, int launchLimit) {
        log.info("launchAllHappyGeologics");
        return launchGeologics(GeologicLaunchs.builder().build().add(GeologicType.Happy, material, launchLimit));
    }

    public int launchGeologics(GeologicLaunchs launchs) {
        log.info("launchGeologics()");
        int launchCount = 0;
        islandCmds.parkMouse();
        StarMenuButtons starMenuButton;
        for (GeologicLaunch launch : launchs) {
            if (GeologicType.Normal.equals(launch.getType())) {
                starMenuButton = StarMenuButtons.NormalGeologic;
            } else if (GeologicType.Happy.equals(launch.getType())) {
                starMenuButton = StarMenuButtons.HappyGeologic;
            } else if (GeologicType.Conscientious.equals(launch.getType())) {
                starMenuButton = StarMenuButtons.ConscientiousGeologic;
            } else {
                throw new IllegalArgumentException("Unknown stepType: " + launch);
            }
            launchCount += starMenu.launchAllGeologicsByImage(starMenuButton, launch.getMaterial(), launch.getLaunchLimit());
        }
        islandCmds.typeESC();
        islandCmds.sleep();
        return launchCount;
    }

    @Deprecated
    public int launchAllConscientiousGeologics(MaterialType material, int launchLimit) {
        log.info("launchAllConscientiousGeologics");
        return launchGeologics(GeologicLaunchs.builder().build().add(GeologicType.Conscientious, material, launchLimit));
    }

    public boolean prepareStarMenu() {
        log.info("prepareStarMenu()");
        return this.prepareStarMenu(StarMenuFilter.ENTDDECK_KUNDSCH_GEOLO);
    }

    public boolean prepareStarMenu(StarMenuFilter filter) {
        log.info("prepareStarMenu() filter: " + filter);
        boolean rv = starMenu.openStarMenu(Optional.of(filter));
        if (rv) {
            islandCmds.sleep();
            islandCmds.typeESC();
            islandCmds.sleep();
        } else {
            log.severe("Missing open star menu.");
            throw new IllegalStateException("Missing open star menu.");
        }
        return rv;
    }

    public boolean prepareStarMenuForGold() {
        log.info("prepareStarMenuForGold()");
        return this.prepareStarMenu(StarMenuFilter.EIGTH_PERCENT);
    }

    public void fetchBookbinderItem() {
        log.info("fetchBookbinderItem");
        islandCmds.parkMouse();
        this.goToSector(Sector.S3);
        if (islandCmds.clickBookbinderBuilding()) {
            islandCmds.sleepX(4);
            if (islandCmds.clickBigOkButton()) {
                // Assumes production is ready
                islandCmds.sleepX(15);
            }
            bookbinderMenu.clickButton(BookbinderMenuButtons.Kompendium);
            islandCmds.sleep();
            if (bookbinderMenu.clickOkButtonBookbinder()) {

            }
        }
        islandCmds.parkMouse();
        islandCmds.typeESC();
        islandCmds.sleep();
        this.goToSector(Sector.S1);
    }

    public void exitDso() {
        log.info("exitDso()");
        islandCmds.typeESC();
        islandCmds.clickExitButton();
        sleepX(10);
    }

    private int buildMines(int limit, MaterialType material, BuildMenuButtons mineButton) {
        log.info("buildMines() limit: " + limit + ", material: " + material);

        int buildCount = 0;
        boolean isBuildMenuPrepared = false;
        islandCmds.parkMouse();
        outerloop:
        for (int i = 0; i < material.msl.length; ++i) {
            MaterialSector sourceSector = material.msl[i];
            for (Sector sector : sourceSector.sectors) {
                goToSector(sector);
                islandCmds.parkMouse();
                Iterator<Match> matches = islandCmds.findAll(sourceSector.pattern);
                if (matches != null) {
                    // we have found a material source icon
                    while (matches.hasNext()) {
                        if (buildCount >= limit) {
                            log.info("limit of builds exceeded");
                            break outerloop;
                        }
                        islandCmds.typeESC();
                        if (!isBuildMenuPrepared) {
                            isBuildMenuPrepared = true;
                            if (!starMenu.openBuildMenu()) {
                                log.warning("Cancel build");
                                break outerloop;
                            }
                            if (!buildMenu.prepareBuildMenuTab(mineButton.tab)) {
                                log.fine("Maybe menu is already prepared");
                            }
                            islandCmds.parkMouse();
                        }
                        if (!starMenu.openBuildMenu()) {
                            log.severe("Cancel build");
                            break outerloop;
                        }
                        if (!buildMenu.buildMine(matches.next(), mineButton)) {
                            log.info("build of mine was not successful");
                            break outerloop;
                        }
                        islandCmds.parkMouse();
                        if (!islandCmds.clickBuildCancelButton()) {
                            ++buildCount;
                        }
                    }
                }
            }
            islandCmds.clickBuildCancelButton();
            islandCmds.typeESC();
        }
        islandCmds.typeESC();
        return buildCount;
    }

    public int buildCopperMines(int limit) {
        log.info("buildCopperMines()");
        return this.buildMines(limit, MaterialType.KU, BuildMenuButtons.CopperMineButton);
    }

    public int buildIronMines(int limit) {
        log.info("buildIronMines()");
        return this.buildMines(limit, MaterialType.EI, BuildMenuButtons.IronMineButton);
    }

    public int buildGoldMines(int limit) {
        log.info("buildGoldMines()");
        int buildCount = this.buildMines(limit, MaterialType.GO, BuildMenuButtons.GoldMineButton);
        return buildCount;
    }

    public int buildColeMines(int limit) {
        log.info("buildColeMines()");
        return this.buildMines(limit, MaterialType.KO, BuildMenuButtons.ColeMineButton);
    }

    public int getBuildQueueSize() {
        log.info("getBuildQueueSize()");
        return buildQueueMenu.getBuildQueueSize();
    }

    /**
     * @param fastFirst False means th following order to build mines: gold, cole, iron, copper. True means start with copper.
     */
    public void buildAllMines(boolean fastFirst) {
        log.info("buildAllMines()");
        int buildCount = 0;
        Integer[] orderIds = {1, 2, 3, 4};
        if (fastFirst) {
            List<Integer> list = Arrays.asList(orderIds);
            Collections.reverse(list);
            orderIds = list.toArray(new Integer[orderIds.length]);
        }
        for (Integer order : orderIds) {
            switch (order) {
                case 1:
                    buildCount = buildCount + buildGoldMines(3);
                    break;
                case 2:
                    buildCount = buildCount + buildColeMines(3);
                    break;
                case 3:
                    buildCount = buildCount + buildIronMines(3);
                    break;
                case 4:
                    buildCount = buildCount + buildCopperMines(3);
                    break;
                default:
                    throw new IllegalStateException("Unsupported order id: " + order);
            }
            if (buildCount > 5) {
                return;
            }
        }
    }

    public void highlightRegions() {
        log.info("highlightRegions()");
        islandCmds.highlightRegion();
        buildMenu.highlightMenuRegion();
        starMenu.highlightMenuRegion();
        bookbinderMenu.highlightMenuRegion();
        buildQueueMenu.highlightMenuRegion();
        questBookCmds.highlightMenuRegion();
    }

    public void launchAllExplorer() {
        log.info("launchAllExplorer");
        launchAllBraveExplorer();        // Mutige
        launchAllSuccessfulExplorer();   // Erfolgreiche
        launchAllWildExplorer();         // Wilde
        launchAllFearlessExplorer();     // Furchlose
        launchAllNormalExplorer();
        islandCmds.typeESC();
    }

    public int launchAllBraveExplorer() {
        log.info("launchAllBraveExplorer");
        return starMenu.launchAllExplorerByImage(pattern("BraveExplorer-icon.png").similar(0.80f));
    }

    public int launchAllSuccessfulExplorer() {
        log.info("launchAllSuccessfulExplorer");
        return starMenu.launchAllExplorerByImage(pattern("SuccessfulExplorer-icon.png").similar(0.80f));
    }

    public int launchAllWildExplorer() {
        log.info("launchAllWildExplorer");
        return starMenu.launchAllExplorerByImage(pattern("WildExplorer-icon.png").similar(0.85f));
    }

    public int launchAllFearlessExplorer() {
        log.info("launchAllFearlessExplorer");
        return starMenu.launchAllExplorerByImage(pattern("FearlessExplorer-icon.png").similar(0.81f));
    }

    public int launchAllNormalExplorer() {
        log.info("launchAllNormalExplorer");
        return starMenu.launchAllExplorerByImage(pattern("NormalExplorer-icon.png").similar(0.80f));
    }

    public void preventScreensaver() {
        log.info("preventScreensaver()");
        islandCmds.clickDsoTab();
        islandCmds.hover();
        starMenu.hover();
        buildQueueMenu.hover();
        islandCmds.typeESC();
    }

    public void fetchRewardMessages() {
        log.info("fetchRewardMessages");
        if (starMenu.openMessageBox()) {
            int msgCount = messageboxMenu.fetchRewardMessages();
            log.info("Fetched reward messages: " + msgCount);
            messageboxMenu.closeMenu();
        } else {
            log.severe("Failed to open message box");
        }
    }
}
