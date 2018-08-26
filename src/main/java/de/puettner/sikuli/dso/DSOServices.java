package de.puettner.sikuli.dso;

import de.puettner.sikuli.dso.commands.os.PlatformCommands;
import de.puettner.sikuli.dso.commands.ui.*;
import lombok.extern.slf4j.Slf4j;
import org.sikuli.script.Match;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Iterator;

import static de.puettner.sikuli.dso.commands.ui.SikuliCommands.pattern;
import static org.sikuli.script.Commands.click;
import static org.sikuli.script.Commands.hover;

@Slf4j
public class DSOServices {

    public static final int MAX_SECTOR_INDEX = 12;
    private final PlatformCommands winCommand = new PlatformCommands();
    private final IslandCommands islandCmds;
    private final BuildMenuCommands buildMenu;
    private final StarMenuCommands starMenu;
    private final BookbinderMenuCommands bookbinderMenu;
    private final BuildQueueMenuCommands buildQueueMenu;

    public DSOServices() {
        this.islandCmds = CommandBuilder.build().buildIslandCommand();
        this.buildMenu = CommandBuilder.build().buildBuildMenuCommands();
        this.starMenu = CommandBuilder.build().buildStarMenuCommands();
        this.bookbinderMenu = CommandBuilder.build().buildBookbinderMenuCommands();
        this.buildQueueMenu = CommandBuilder.build().buildBuildQueueMenuCommands();
    }

    public void startDsoApp() {
        log.info("startDsoApp");
        if (!winCommand.isChromeRunning()) {
            throw new NotImplementedException();
        }
        islandCmds.switchToBrowser();
        islandCmds.sleep(1);
        islandCmds.clickDsoTab();
        if (islandCmds.clickLetsPlayButtonIfExists()) {
            this.closeWelcomeDialog();
        } else {
            log.info("expect running DSO app");
        }
    }

    /**
     *
     */
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
        for (int i = 0; i <= MAX_SECTOR_INDEX; ++i) {
            this.goToSector(i);
            islandCmds.sleep(1);
        }
        goToSector(1);
        islandCmds.sleep(1);
    }

    void goToSector(int i) {
        log.info("goToSector");
        if (i < 0 || i > MAX_SECTOR_INDEX) {
            throw new IllegalArgumentException();
        }
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
        //int[] quadranten = {8};
        int[] quadranten = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        for (int quadrant : quadranten) {
            this.goToSector(quadrant);
            Iterator<Match> icons = islandCmds.findAll(pattern("Collectable-icon.png").targetOffset(5, 6));
            while (icons.hasNext()) {
                Match icon = icons.next();
                log.info("Sammelgegenstand gefunden");
                hover(icon);
                islandCmds.sleep(1);
                //click(icon);
                //sleep(1);
            }
        }
        return true;
    }

    public boolean solveGuildQuest() {
        log.info("solveGuildQuest");
        islandCmds.openQuestBook();
        click(pattern("GuildQuestMenuItem-icon.png").targetOffset(6, 57));
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
        this.goToSector(3);
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
        this.goToSector(1);
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

    public int buildColeMines(int limit) {
        int[] sectors = {7, 6};
        return this.buildMines(limit, MaterialType.KO, BuildMenuButtons.ColeMineButton, BuildMenuButtons.RaisedBuildingButton, sectors);
    }

    private int buildMines(int limit, MaterialType material, BuildMenuButtons mineButton, BuildMenuButtons buildingButton, int[] sectors) {
        log.info("buildCopperMines");

        int buildCount = 0;
        islandCmds.parkMouse();
        outerloop: for(int sector : sectors) {
            goToSector(sector);
            //             islandCmds.hightlightRegions(); islandCmds.sleep(1);
            islandCmds.parkMouse();
            Iterator<Match> matches = islandCmds.findMines(material);
            while (matches.hasNext()) {
                if (buildCount >= limit) {
                    break outerloop;
                }
                islandCmds.typeESC();
                if (buildCount == 0) {
                    prepareBuildMenu(buildingButton);
                }
                starMenu.openBuildMenu();
                islandCmds.sleep(1);
                if (buildMenu.isRaisedBuildingMenuDisabled()) {
                    log.info("Build-Menu is disabled");
                    break outerloop;
                } else {
                    buildMenu.clickButton(mineButton);
                    islandCmds.sleep(1);
                    Match match = matches.next();
                    //                match.hover();
                    match.click();
                    islandCmds.sleep(1);
                    buildCount++;
                }
            }
        }
        islandCmds.clickBuildCancelButton();
        islandCmds.typeESC();
        return buildCount;
    }

    private void prepareBuildMenu(BuildMenuButtons buildingType) {
        log.info("prepareBuildMenu");
        starMenu.openBuildMenu();
        buildMenu.clickButton(buildingType);
        islandCmds.typeESC();
    }


    public int buildCopperMines(int limit) {
        int[] sectors = {2, 4};
        return this.buildMines(limit, MaterialType.KU, BuildMenuButtons.CopperMineButton, BuildMenuButtons.ImprovedBuildingButton, sectors);
    }

    public int buildIronMines(int limit) {
        int[] sectors = {10, 3, 4, 5, 6, 8, 9};
        return this.buildMines(limit, MaterialType.EI, BuildMenuButtons.IronMineButton, BuildMenuButtons.RaisedBuildingButton, sectors);
    }

    public int buildGoldMines(int limit) {
        int[] sectors = {2, 9, 10};
        return this.buildMines(limit, MaterialType.GO, BuildMenuButtons.GoldMineButton, BuildMenuButtons.RaisedBuildingButton, sectors);
    }

    void sleep(int i) {
        islandCmds.sleep(i);
    }
}
