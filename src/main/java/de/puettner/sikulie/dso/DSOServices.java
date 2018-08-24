package de.puettner.sikulie.dso;

import lombok.extern.slf4j.Slf4j;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Match;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Iterator;

import static org.sikuli.script.Commands.click;
import static org.sikuli.script.Commands.hover;

@Slf4j
public class DSOServices {

    private final PlatformCommands winCommand = new PlatformCommands();
    private final SikuliCommands sikuliComd;

    public DSOServices() {
        sikuliComd = SikuliCommands.build();
    }

    // **********************************************************************************************

    //    private Pattern pattern(String filename) {
    //        return sikuliComd.pattern(filename);
    //    }

    // **********************************************************************************************

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
            sikuliComd.type(quadrant);
            try {
                Iterator<Match> icons = sikuliComd.findAll(sikuliComd.pattern("Collectable-icon.png").targetOffset(5, 6));
                while (icons.hasNext()) {
                    Match icon = icons.next();
                    log.info("Sammelgegenstand gefunden");
                    hover(icon);
                    sikuliComd.sleep(1);
                    //click(icon);
                    //sleep(1);
                }
            } catch (FindFailed f) {
                log.info("No Collactables found");
                log.error(f.getMessage(), f);
            }
        }
        return true;
    }

    public boolean solveGuildQuest() {
        log.info("solveGuildQuest");
        sikuliComd.openQuestBook();
        click(sikuliComd.pattern("GuildQuestMenuItem-icon.png").targetOffset(6, 57));
        sikuliComd.clickSmallOkButton();
        sikuliComd.sleep(20);
        sikuliComd.clickSmallOkButton();
        return true;
    }

    public boolean launchAllWildExplorer() {
        log.info("launchAllWildExplorer");
        sikuliComd.launchAllExplorerByImage(sikuliComd.pattern("WildExplorer-icon.png").similar(0.85f));
        return true;
    }

    public boolean launchAllNormalExplorer() {
        log.info("launchAllNormalExplorer");
        sikuliComd.launchAllExplorerByImage(sikuliComd.pattern("NormalExplorer-icon.png").similar(0.80f));

        return true;
    }

    public boolean launchAllSuccessfulExplorer() {
        log.info("launchAllSuccessfulExplorer");
        sikuliComd.launchAllExplorerByImage(sikuliComd.pattern("SuccessfulExplorer-icon.png").similar(0.80f));
        return true;
    }

    public boolean launchAllFearlessExplorer() {
        log.info("launchAllFearlessExplorer");
        sikuliComd.launchAllExplorerByImage(sikuliComd.pattern("FearlessExplorer-icon.png").similar(0.81f));
        return true;
    }

    public boolean launchAllBraveExplorer() {
        log.info("launchAllBraveExplorer");
        sikuliComd.launchAllExplorerByImage(sikuliComd.pattern("BraveExplorer-icon.png").similar(0.80f));
        return true;
    }

    public boolean launchAllHappyGeologics(String material, int launchLimit) {
        log.info("launchAllHappyGeologics");
        sikuliComd.parkMouse();
        sikuliComd.launchAllGeologicsByImage("HappyGeologic-icon.png", material, launchLimit);
        return true;
    }

    /**
     * Gewissenhafte Geologen
     */
    public boolean launchAllConscientiousGeologics(String material, int launchLimit) {
        log.info("launchAllConscientiousGeologics");
        sikuliComd.launchAllGeologicsByImage(sikuliComd.pattern("ConscientiousGeologic-icon.png").similar(0.80f), material, launchLimit);
        return true;
    }

    public boolean launchAllNormalGeologics(String material, int launchLimit) {
        log.info("launchAllNormalGeologics");
        sikuliComd.launchAllGeologicsByImage(sikuliComd.pattern("NormalGeologic-icon.png").similar(0.80f), material, launchLimit);
        return true;
    }

    public void prepareStarMenuForGeologicsNExplorers() {
        log.info("prepareStarMenuForGeologicsNExplorers");
        sikuliComd.openStarMenu("entdeck|kundsch|geolo");
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
        }
        sikuliComd.clickNextBook("KO");
        sikuliComd.sleep(1);
        if (sikuliComd.clickOkButtonBookbinder()) {

        }
        sikuliComd.typeESC();
        sikuliComd.sleep(1);
        this.goToSector(1);
        sikuliComd.parkMouse();
    }

    private void goToSector(int i) {
        log.info("goToSector");
        if (i < 0 || i > 9) {
            throw new IllegalArgumentException();
        }
        sikuliComd.type(i);
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

    public void buildCopperMines() {
        sikuliComd.buildMine("");
    }
}
