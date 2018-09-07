package de.puettner.sikuli.dso;

import de.puettner.sikuli.dso.commands.os.WindowsPlatform;
import de.puettner.sikuli.dso.commands.ui.*;
import lombok.extern.java.Log;
import org.sikuli.script.Match;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Iterator;
import java.util.Optional;

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

    DSOService(WindowsPlatform winCommand, IslandCommands islandCmds, BuildMenu buildMenu, StarMenu starMenu,
               BookbinderMenu bookbinderMenu, BuildQueueMenu buildQueueMenu, QuestBookMenu questBookCmds) {
        this.winCommand = winCommand;
        this.islandCmds = islandCmds;
        this.buildMenu = buildMenu;
        this.starMenu = starMenu;
        this.bookbinderMenu = bookbinderMenu;
        this.buildQueueMenu = buildQueueMenu;
        this.questBookCmds = questBookCmds;
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
            islandCmds.sleep(3000);
            timeout -= 1;
            if (islandCmds.existsAvatar()) {
                okButtonTimeout -= 1;
                if (okButtonTimeout < 0 || islandCmds.clickSmallOkButton()) {
                    islandCmds.sleep();
                    timeout = 0;
                    // Login Bonus
                    if (islandCmds.clickLoginBonusButton()) {
                        this.visitAllSectors();
                    }
                }
            }
        }
        sleep();
    }

    private void visitAllSectors() {
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
            islandCmds.type(i - 10);
            if (i >= 10 && i <= 12) {
                islandCmds.dragNdrop(200, -750);
            }
        }
        this.sleep(1000);
    }

    void sleep(int ms) {
        islandCmds.sleep(ms);
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
                    islandCmds.sleep(20000);
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
                    islandCmds.sleep(20000);
                    if (islandCmds.clickSmallOkButton()) {
                        islandCmds.sleep(3000);
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
                islandCmds.dragNdrop(0, -100);
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
                        islandCmds.sleep(4000);
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
                throw new IllegalArgumentException("Unknown type: " + launch);
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

    private boolean prepareStarMenu(StarMenuFilter filter) {
        log.info("prepareStarMenu() filter: " + filter);
        boolean rv = starMenu.openStarMenu(Optional.of(filter));
        islandCmds.sleep();
        islandCmds.typeESC();
        islandCmds.sleep();
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
            islandCmds.sleep(2000);
            if (islandCmds.clickBigOkButton()) {
                // Assumes production is ready
                islandCmds.sleep(15000);
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
        sleep(5000);
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
                            if (!buildMenu.prepareBuildMenu(mineButton.tab)) {
                                log.warning("Cancel build");
                                break outerloop;
                            }
                        }
                        if (!starMenu.openBuildMenu()) {
                            log.warning("Cancel build");
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

    public void buildAllMines() {
        log.info("buildAllMines()");
        int buildCount = buildGoldMines(3);
        if (buildCount > 5) {
            return;
        }
        buildCount = buildCount + buildIronMines(3);
        if (buildCount > 5) {
            return;
        }
        buildCount = buildCount + buildColeMines(3);
        if (buildCount > 5) {
            return;
        }
        buildCount = buildCount + buildCopperMines(3);
        if (buildCount > 5) {
            return;
        }
    }

    public void highlightRegions() {
        log.info("highlightRegions()");
        islandCmds.hightlightRegions();
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
}
