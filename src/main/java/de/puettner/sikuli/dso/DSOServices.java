package de.puettner.sikuli.dso;

import de.puettner.sikuli.dso.commands.*;
import lombok.extern.slf4j.Slf4j;
import org.sikuli.script.Match;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Iterator;

import static de.puettner.sikuli.dso.commands.SikuliCommands.pattern;
import static org.sikuli.script.Commands.click;
import static org.sikuli.script.Commands.hover;

@Slf4j
public class DSOServices {

    private final PlatformCommands winCommand = new PlatformCommands();
    private final SikuliCommands sikuliComd;
    private final BuildMenuCommands buildMenu;
    private final StarMenuCommands starMenu;
    private final BookbinderMenuCommands bookbinderMenu;


    public DSOServices() {
        sikuliComd = CommandBuilder.build().buildSikuliCommand();
        this.buildMenu = CommandBuilder.build().buildBuildMenuCommands();
        this.starMenu = CommandBuilder.build().buildStarMenuCommands();
        this.bookbinderMenu = CommandBuilder.build().buildBookbinderMenuCommands();
    }

    public void startDsoApp() {
        log.info("startDsoApp");
        if (!winCommand.isChromeRunning()) {
            throw new NotImplementedException();
        }
        sikuliComd.switchToBrowser();
        sikuliComd.sleep(1);
        sikuliComd.clickDsoTab();
        if (sikuliComd.clickLetsPlayButtonIfExists()) {
            this.closeWelcomeDialog();
        } else {
            log.info("expect running DSO app");
        }
    }

    public void closeWelcomeDialog() {
        log.info("closeWelcomeDialog");
        int timeout = 300;
        int okButtonTimeout = 3;
        while (timeout > 0) {
            sikuliComd.sleep(1);
            timeout -= 1;
            if (sikuliComd.existsAvatar()) {
                okButtonTimeout -= 1;
                if (okButtonTimeout < 0 || sikuliComd.clickSmallOkButton()) {
                    sikuliComd.sleep(1);
                    timeout = 0;
                    // Login Bonus
                    sikuliComd.clickLoginBonusButton();
                }
            }
        }
    }

    public boolean solveDailyQuest() {
        log.info("solveDailyQuest");
        sikuliComd.openQuestBook();
        if (sikuliComd.existsDailyQuestMenuIem()) {
            sikuliComd.clickSmallOkButton();
            sikuliComd.sleep(20);
            sikuliComd.clickSmallOkButton();
        }
        return true;
    }

    public boolean findAllCollectables() {
        log.info("findAllCollectables");
        //int[] quadranten = {8};
        int[] quadranten = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        for (int quadrant : quadranten) {
            this.goToSector(quadrant);
            Iterator<Match> icons = sikuliComd.findAll(pattern("Collectable-icon.png").targetOffset(5, 6));
            while (icons.hasNext()) {
                Match icon = icons.next();
                log.info("Sammelgegenstand gefunden");
                hover(icon);
                sikuliComd.sleep(1);
                //click(icon);
                //sleep(1);
            }
        }
        return true;
    }

    private void goToSector(int i) {
        log.info("goToSector");
        if (i < 0 || i > 9) {
            throw new IllegalArgumentException();
        }
        sikuliComd.type(i);
    }

    public boolean solveGuildQuest() {
        log.info("solveGuildQuest");
        sikuliComd.openQuestBook();
        click(pattern("GuildQuestMenuItem-icon.png").targetOffset(6, 57));
        sikuliComd.clickSmallOkButton();
        sikuliComd.sleep(20);
        sikuliComd.clickSmallOkButton();
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
        sikuliComd.parkMouse();
        return starMenu.launchAllGeologicsByImage(pattern("HappyGeologic-icon.png").similar(0.90f), material, launchLimit);
    }

    public int launchAllNormalGeologics(MaterialType material, int launchLimit) {
        log.info("launchAllNormalGeologics");
        return starMenu.launchAllGeologicsByImage(pattern("NormalGeologic-icon.png").similar(0.90f), material, launchLimit);
    }

    /**
     * Gewissenhafte Geologen
     */
    public int launchAllConscientiousGeologics(MaterialType material, int launchLimit) {
        log.info("launchAllConscientiousGeologics");
        return starMenu.launchAllGeologicsByImage(pattern("ConscientiousGeologic-icon.png").similar(0.90f), material, launchLimit);
    }

    public void prepareStarMenu() {
        log.info("prepareStarMenu");
        starMenu.openStarMenu("entdeck|kundsch|geolo");
        sikuliComd.typeESC();
    }

    public void fetchBookbinderItem() {
        log.info("fetchBookbinderItem");
        sikuliComd.parkMouse();
        this.goToSector(3);
        sikuliComd.clickBookbinderBuilding();
        sikuliComd.sleep(2);
        if (sikuliComd.clickBigOkButton()) {
            // Assumes production is ready
            sikuliComd.sleep(15);
        }
        bookbinderMenu.clickButton(BookbinderMenuButtons.Kompendium);
        sikuliComd.sleep(1);
        if (bookbinderMenu.clickOkButtonBookbinder()) {

        }
        sikuliComd.typeESC();
        sikuliComd.sleep(1);
        this.goToSector(1);
        sikuliComd.parkMouse();
    }

    void switchToBrowser() {
        log.info("switchToBrowser");
        sikuliComd.switchToBrowser();
    }

    public void exitDso() {
        log.info("exitDso()");
        sikuliComd.typeESC();
        sikuliComd.clickExitButton();
    }

    public int buildColeMines(int limit) {
        int[] sectors = {7, 6};
        return this.buildMines(limit, MaterialType.KO, BuildMenuButtons.RaisedBuildingButton, BuildMenuButtons.ColeMineButton, sectors);
    }

    private int buildMines(int limit, MaterialType material, BuildMenuButtons buildingButton, BuildMenuButtons mineButton, int[] sectors) {
        log.info("buildCopperMines");

        int buildCount = 0;
        sikuliComd.parkMouse();
        outerloop: for(int sector : sectors) {
            goToSector(sector);
            Iterator<Match> matches = sikuliComd.findMines(material);
            while (matches.hasNext()) {
                if (buildCount >= limit) {
                    break outerloop;
                }
                sikuliComd.typeESC();
                if (buildCount == 0) {
                    prepareBuildMenu(buildingButton);
                }
                starMenu.openBuildMenu();
                sikuliComd.sleep(1);
                buildMenu.clickButton(mineButton);
                sikuliComd.sleep(1);
                Match match = matches.next();
//                match.hover();
                match.click();
                sikuliComd.sleep(1);
                buildCount++;
            }
        }
        sikuliComd.clickBuildCancelButton();
        sikuliComd.typeESC();
        return buildCount;
    }

    private void prepareBuildMenu(BuildMenuButtons buildingType) {
        log.info("prepareBuildMenu");
        starMenu.openBuildMenu();
        buildMenu.clickButton(buildingType);
        sikuliComd.typeESC();
    }

    public int buildIronMines(int limit) {
        int[] sectors = {6, 7, 9, 4};
        return this.buildMines(limit, MaterialType.EI, BuildMenuButtons.RaisedBuildingButton, BuildMenuButtons.CopperMineButton, sectors);
    }

    public int buildCopperMines(int limit) {
        int[] sectors = {2, 4};
        return this.buildMines(limit, MaterialType.KU, BuildMenuButtons.ImprovedBuildingButton, BuildMenuButtons.CopperMineButton, sectors);
    }

    void sleep(int i) {
        sikuliComd.sleep(i);
    }
}
