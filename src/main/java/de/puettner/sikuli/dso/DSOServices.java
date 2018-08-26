package de.puettner.sikuli.dso;

import de.puettner.sikuli.dso.commands.os.WindowsPlatform;
import de.puettner.sikuli.dso.commands.ui.*;
import lombok.extern.slf4j.Slf4j;
import org.sikuli.script.Match;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Iterator;

import static de.puettner.sikuli.dso.commands.ui.SikuliCommands.pattern;

@Slf4j
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
        islandCmds.switchToBrowser();
        islandCmds.sleep(1);
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
            islandCmds.sleep(1);
            timeout -= 1;
            if (islandCmds.existsAvatar()) {
                okButtonTimeout -= 1;
                if (okButtonTimeout < 0 || islandCmds.clickSmallOkButton()) {
                    islandCmds.sleep(1);
                    timeout = 0;
                    // Login Bonus
                    if (islandCmds.clickLoginBonusButton()) {
                        this.warmupIslandAfterLogin();
                    }
                }
            }
        }
    }

    private void warmupIslandAfterLogin() {
        for (Sector sector : Sector.values()) {
            this.goToSector(sector);
            islandCmds.sleep(1);
        }
        goToSector(Sector.S1);
        islandCmds.sleep(1);
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
    }

    public boolean solveDailyQuest() {
        log.info("solveDailyQuest");
        islandCmds.openQuestBook();
        if (islandCmds.existsDailyQuestMenuIem()) {
            islandCmds.clickSmallOkButton();
            islandCmds.sleep(20);
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
            IslandButtons[] collectableIcons = {IslandButtons.CollectableIconTwo, IslandButtons.CollectableIconOne, IslandButtons
                    .CollectableIconThree};
            for (IslandButtons collectableIcon : collectableIcons) {
                Iterator<Match> icons = islandCmds.findAll(collectableIcon.pattern);
                while (icons.hasNext()) {
                    Match match = icons.next();
                    log.info("Sammelgegenstand gefunden. " + match);
                    match.doubleClick();
                    islandCmds.sleep(1);
                    islandCmds.typeESC();
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
        islandCmds.sleep(20);
        islandCmds.clickSmallOkButton();
        return true;
    }

    public boolean launchAllWildExplorer() {
        log.info("launchAllWildExplorer");
        starMenu.launchAllExplorerByImage(pattern("WildExplorer-icon.png").similar(0.85f));
        return true;
    }

    public boolean launchAllNormalExplorer() {
        log.info("launchAllNormalExplorer");
        starMenu.launchAllExplorerByImage(pattern("NormalExplorer-icon.png").similar(0.80f));

        return true;
    }

    public boolean launchAllSuccessfulExplorer() {
        log.info("launchAllSuccessfulExplorer");
        starMenu.launchAllExplorerByImage(pattern("SuccessfulExplorer-icon.png").similar(0.80f));
        return true;
    }

    public boolean launchAllFearlessExplorer() {
        log.info("launchAllFearlessExplorer");
        starMenu.launchAllExplorerByImage(pattern("FearlessExplorer-icon.png").similar(0.81f));
        return true;
    }

    public int launchAllBraveExplorer() {
        log.info("launchAllBraveExplorer");
        return starMenu.launchAllExplorerByImage(pattern("BraveExplorer-icon.png").similar(0.80f));
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
        log.info("prepareStarMenu");
        starMenu.openStarMenu("entdeck|kundsch|geolo");
        islandCmds.typeESC();
    }

    public void fetchBookbinderItem() {
        log.info("fetchBookbinderItem");
        islandCmds.parkMouse();
        this.goToSector(Sector.S3);
        islandCmds.clickBookbinderBuilding();
        islandCmds.sleep(2);
        if (islandCmds.clickBigOkButton()) {
            // Assumes production is ready
            islandCmds.sleep(15);
        }
        bookbinderMenu.clickButton(BookbinderMenuButtons.Kompendium);
        islandCmds.sleep(1);
        if (bookbinderMenu.clickOkButtonBookbinder()) {

        }
        islandCmds.typeESC();
        islandCmds.sleep(1);
        this.goToSector(Sector.S1);
        islandCmds.parkMouse();
    }

    void switchToBrowser() {
        log.info("switchToBrowser");
        islandCmds.switchToBrowser();
    }

    public void exitDso() {
        log.info("exitDso()");
        islandCmds.typeESC();
        islandCmds.clickExitButton();
    }

    private int buildMines(int limit, MaterialType material, BuildMenuButtons mineButton, BuildMenuButtons buildingButton) {
        log.info("buildCopperMines");

        int buildCount = 0;
        islandCmds.parkMouse();
        outerloop:
        for (int i = 0; i < material.msl.length; ++i) {
            MaterialSector sourceSector = material.msl[i];
            for (Sector sector : sourceSector.sectors) {
                goToSector(sector);
                // islandCmds.hightlightRegions();
                islandCmds.parkMouse();
                Iterator<Match> matches = islandCmds.findAll(sourceSector.pattern);
                while (matches.hasNext()) {
                    if (buildCount >= limit) {
                        break outerloop;
                    }
                    if (buildCount == 0) {
                        prepareBuildMenu(buildingButton);
                    }
                    if (!buildMine(matches.next(), limit, mineButton, buildingButton)) {
                        break outerloop;
                    }
                    ++buildCount;
                }
            }
            islandCmds.clickBuildCancelButton();
            islandCmds.typeESC();
        }
        return buildCount;
    }

    private boolean buildMine(Match match, int limit, BuildMenuButtons mineButton, BuildMenuButtons buildingButton) {
        log.info("buildCopperMine");
        islandCmds.typeESC();
        starMenu.openBuildMenu();
        if (buildMenu.isRaisedBuildingMenuDisabled()) {
            log.info("Build-Menu is disabled");
            return false;
        }
        buildMenu.clickButton(mineButton);
        islandCmds.sleep(1);
        // Match match = matches.next();
        //                match.hover();
        match.click();
        islandCmds.sleep(1);
        return true;
    }

    private void prepareBuildMenu(BuildMenuButtons buildingType) {
        log.info("prepareBuildMenu");
        starMenu.openBuildMenu();
        buildMenu.clickButton(buildingType);
        islandCmds.typeESC();
    }

    void sleep(int i) {
        islandCmds.sleep(i);
    }

    public int buildCopperMines(int limit) {
        return this.buildMines(limit, MaterialType.KU, BuildMenuButtons.CopperMineButton, BuildMenuButtons.ImprovedBuildingButton);
    }

    public int buildIronMines(int limit) {
        return this.buildMines(limit, MaterialType.EI, BuildMenuButtons.IronMineButton, BuildMenuButtons.RaisedBuildingButton);
    }

    public int buildGoldMines(int limit) {
        int buildCount = this.buildMines(limit, MaterialType.GO, BuildMenuButtons.GoldMineButton, BuildMenuButtons.RaisedBuildingButton);
        return buildCount;
    }

    public int buildColeMines(int limit) {
        return this.buildMines(limit, MaterialType.KO, BuildMenuButtons.ColeMineButton, BuildMenuButtons.RaisedBuildingButton);
    }

    public void buildAllMines() {
        if (buildQueueMenu.getBuildQueueSize() < 3) {
            buildGoldMines(3);
        }
        if (buildQueueMenu.getBuildQueueSize() < 3) {
            buildIronMines(3);
        }
        if (buildQueueMenu.getBuildQueueSize() < 3) {
            buildColeMines(3);
        }
        if (buildQueueMenu.getBuildQueueSize() < 3) {
            buildCopperMines(3);
        }
    }
}
