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
public class DSOServices {

    private final WindowsPlatform winCommand;
    private final IslandCommands islandCmds;
    private final BuildMenuCommands buildMenu;
    private final StarMenuCommands starMenu;
    private final BookbinderMenuCommands bookbinderMenu;
    private final BuildQueueMenuCommands buildQueueMenu;
    private final QuestBookMenuCommands questBookCmds;

    DSOServices(WindowsPlatform winCommand, IslandCommands islandCmds, BuildMenuCommands buildMenu, StarMenuCommands starMenu,
                BookbinderMenuCommands bookbinderMenu, BuildQueueMenuCommands buildQueueMenu, QuestBookMenuCommands questBookCmds) {
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
        islandCmds.focusBrowser();
        islandCmds.sleep();
        islandCmds.clickDsoTab();
        if (islandCmds.clickLetsPlayButtonIfExists()) {
            this.closeWelcomeDialog();
        } else {
            log.info("expected a running DSO app");
        }
    }

    public void closeWelcomeDialog() {
        log.info("closeWelcomeDialog");
        int timeout = 300;
        int okButtonTimeout = 3;
        while (timeout > 0) {
            islandCmds.sleep();
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
    }

    private void visitAllSectors() {
        for (Sector sector : Sector.values()) {
            this.goToSector(sector);
        }
        goToSector(Sector.S1);
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

    void sleep(int i) {
        islandCmds.sleep(i);
    }

    public boolean solveDailyQuest() {
        log.info("solveDailyQuest");
        islandCmds.openQuestBook();
        if (islandCmds.existsDailyQuestMenuIem()) {
            islandCmds.clickSmallOkButton();
            islandCmds.sleep(20000);
            islandCmds.clickSmallOkButton();
        }
        return true;
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
                        islandCmds.sleep(5000);
                        islandCmds.typeESC();
                    }
                } else {
                    log.info("No Collectables found");
                }
            }
        }
        return true;
    }

    public boolean solveGuildQuest() {
        log.info("solveGuildQuest");
        islandCmds.openQuestBook();
        questBookCmds.clickButton(QuestBookMenuButtons.GuildQuestMenuItem);
        islandCmds.clickSmallOkButton();
        islandCmds.sleep(20000);
        islandCmds.clickSmallOkButton();
        islandCmds.sleep(3000);
        islandCmds.typeESC();
        return true;
    }

    public int launchAllGeologics(MaterialType material, int launchLimit) {
        launchLimit = launchLimit - this.launchAllHappyGeologics(material, launchLimit);
        launchLimit = launchLimit - this.launchAllNormalGeologics(material, launchLimit);
        launchLimit = launchLimit - this.launchAllConscientiousGeologics(material, launchLimit);
        return launchLimit;
    }

    public int launchAllHappyGeologics(MaterialType material, int launchLimit) {
        log.info("launchAllHappyGeologics");
        islandCmds.parkMouse();
        return starMenu.launchAllGeologicsByImage(StarMenuButtons.HappyGeologic, material, launchLimit);
    }

    public int launchAllNormalGeologics(MaterialType material, int launchLimit) {
        log.info("launchAllNormalGeologics");
        return starMenu.launchAllGeologicsByImage(StarMenuButtons.NormalGeologic, material, launchLimit);
    }

    /**
     * Gewissenhafte Geologen
     */
    public int launchAllConscientiousGeologics(MaterialType material, int launchLimit) {
        log.info("launchAllConscientiousGeologics");
        return starMenu.launchAllGeologicsByImage(StarMenuButtons.ConscientiousGeologic, material, launchLimit);
    }

    public void prepareStarMenu() {
        this.prepareStarMenu(StarMenuFilter.ENTDDECK_KUNDSCH_GEOLO);
    }

    public void prepareStarMenu(StarMenuFilter filter) {
        log.info("prepareStarMenu");
        starMenu.openStarMenu(Optional.of(filter));
        islandCmds.typeESC();
    }

    public void prepareStarMenuForGold() {
        this.prepareStarMenu(StarMenuFilter.EIGTH_PERCENT);
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
        islandCmds.sleep();
        islandCmds.typeESC();
        this.goToSector(Sector.S1);
    }

    public void switchToBrowser() {
        log.info("focusBrowser");
        islandCmds.focusBrowser();
    }

    public void exitDso() {
        log.info("exitDso()");
        islandCmds.typeESC();
        islandCmds.clickExitButton();
    }

    private int buildMines(int limit, MaterialType material, BuildMenuButtons mineButton) {
        log.info("buildCopperMines");

        int buildCount = 0;
        islandCmds.parkMouse();
        outerloop:
        for (int i = 0; i < material.msl.length; ++i) {
            MaterialSector sourceSector = material.msl[i];
            for (Sector sector : sourceSector.sectors) {
                goToSector(sector);
                islandCmds.parkMouse();
                Iterator<Match> matches = islandCmds.findAll(sourceSector.pattern);
                if (matches != null) {
                    while (matches.hasNext()) {
                        if (buildCount >= limit) {
                            log.info("limit of builds exceeded");
                            break outerloop;
                        }
                        islandCmds.typeESC();
                        starMenu.openBuildMenu();
                        if (!buildMenu.buildMine(matches.next(), mineButton)) {
                            log.info("build of mine was not successful");
                            break outerloop;
                        }
                        islandCmds.parkMouse();
                        ++buildCount;
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
        starMenu.openBuildMenu();
        buildMenu.prepareBuildMenu(BuildMenuButtons.ImprovedBuildingButton);
        return this.buildMines(limit, MaterialType.KU, BuildMenuButtons.CopperMineButton);
    }

    public int buildIronMines(int limit) {
        starMenu.openBuildMenu();
        buildMenu.prepareBuildMenu(BuildMenuButtons.RaisedBuildingButton);
        return this.buildMines(limit, MaterialType.EI, BuildMenuButtons.IronMineButton);
    }

    public int buildGoldMines(int limit) {
        starMenu.openBuildMenu();
        buildMenu.prepareBuildMenu(BuildMenuButtons.RaisedBuildingButton);
        int buildCount = this.buildMines(limit, MaterialType.GO, BuildMenuButtons.GoldMineButton);
        return buildCount;
    }

    public int buildColeMines(int limit) {
        starMenu.openBuildMenu();
        buildMenu.prepareBuildMenu(BuildMenuButtons.RaisedBuildingButton);
        return this.buildMines(limit, MaterialType.KO, BuildMenuButtons.ColeMineButton);
    }

    public int getBuildQueueSize() {
        return buildQueueMenu.getBuildQueueSize();
    }

    public void buildAllMines() {
        //        if (buildQueueMenu.getBuildQueueSize() < 3) {
        //            buildGoldMines(3);
        //        }
        //        if (buildQueueMenu.getBuildQueueSize() < 3) {
        //            buildIronMines(3);
        //        }
        //        if (buildQueueMenu.getBuildQueueSize() < 3) {
        //            buildColeMines(3);
        //        }
        //        if (buildQueueMenu.getBuildQueueSize() < 3) {
        //            buildCopperMines(3);
        //        }
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
        islandCmds.hightlightRegions();
        buildMenu.highlightMenuRegion();
        starMenu.highlightMenuRegion();
        bookbinderMenu.highlightMenuRegion();
        buildQueueMenu.highlightMenuRegion();
        questBookCmds.highlightMenuRegion();
    }

    public void launchAllExplorer() {
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

    public boolean launchAllSuccessfulExplorer() {
        log.info("launchAllSuccessfulExplorer");
        starMenu.launchAllExplorerByImage(pattern("SuccessfulExplorer-icon.png").similar(0.80f));
        return true;
    }

    public boolean launchAllWildExplorer() {
        log.info("launchAllWildExplorer");
        starMenu.launchAllExplorerByImage(pattern("WildExplorer-icon.png").similar(0.85f));
        return true;
    }

    public boolean launchAllFearlessExplorer() {
        log.info("launchAllFearlessExplorer");
        starMenu.launchAllExplorerByImage(pattern("FearlessExplorer-icon.png").similar(0.81f));
        return true;
    }

    public boolean launchAllNormalExplorer() {
        log.info("launchAllNormalExplorer");
        starMenu.launchAllExplorerByImage(pattern("NormalExplorer-icon.png").similar(0.80f));
        return true;
    }
}
