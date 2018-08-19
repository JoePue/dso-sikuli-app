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

    public void startDsoApp() {
        log.info("startDsoApp");
        if (!winCommand.isChromeRunning()) {
            throw new NotImplementedException();
        }
        sikuliComd.switchToBrowser();
        sikuliComd.highlightAppRegion();
        sikuliComd.clickDsoTab();
        if (sikuliComd.existsLetsPlayButton()) {
            sikuliComd.clickLetsPlayButton();
            sikuliComd.closeWelcomeDialog();
        } else {
            log.info("expect running DSO app");
        }
    }

    public void closeWelcomeDialog() {

    }

    public boolean solveDailyQuest() {
        sikuliComd.openQuestBook();
        if (sikuliComd.existsDailyQuestMenuIem()) {
            sikuliComd.clickOkButton();
            sikuliComd.sleep(20);
            sikuliComd.clickOkButton();
        }
        return true;
    }

    public boolean findAllCollectables() {
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
        sikuliComd.openQuestBook();
        click(sikuliComd.pattern("GuildQuestMenuItem-icon.png").targetOffset(6, 57));
        sikuliComd.clickOkButton();
        sikuliComd.sleep(20);
        sikuliComd.clickOkButton();
        return true;
    }

    public boolean launchAllWildExplorer() {
        sikuliComd.launchAllExplorerByImage(sikuliComd.pattern("WildExplorer-icon.png").similar(0.80f));
        return true;
    }

    public boolean launchAllNormalExplorer() {
        sikuliComd.launchAllExplorerByImage(sikuliComd.pattern("NormalExplorer-icon.png").similar(0.80f));

        return true;
    }

    public boolean launchAllSuccessfulExplorer() {
        sikuliComd.launchAllExplorerByImage(sikuliComd.pattern("SuccessfulExplorer-icon.png").similar(0.80f));
        return true;
    }

    public boolean launchAllFearlessExplorer() {
        sikuliComd.launchAllExplorerByImage(sikuliComd.pattern("FearlessExplorer-icon.png").similar(0.81f));
        return true;
    }

    public boolean launchAllBraveExplorer() {
        sikuliComd.launchAllExplorerByImage(sikuliComd.pattern("BraveExplorer-icon.png").similar(0.80f));
        return true;
    }

    // gewissenhaft
    public boolean launchAllHappyGeologics(String material, int launchLimit) {
        sikuliComd.parkMouse();
        sikuliComd.launchAllGeologicsByImage("HappyGeologic-icon.png", material, launchLimit);
        return true;
    }

    public boolean launchAllConscientiousGeologics(String material, int launchLimit) {
        sikuliComd.launchAllGeologicsByImage(sikuliComd.pattern("ConscientiousGeologic-icon.png").similar(0.80f), material, launchLimit);
        return true;
    }

    public boolean launchAllNormalGeologics(String material, int launchLimit) {
        sikuliComd.launchAllGeologicsByImage(sikuliComd.pattern("NormalGeologic-icon .png").similar(0.80f), material, launchLimit);
        return true;
    }

    public void prepareStarMenuForGeologicsNExplorers() {
        sikuliComd.openStarMenu("entdeck|kundsch|geolo");
    }
}
