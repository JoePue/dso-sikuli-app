package de.puettner.sikulie.dso;

import lombok.extern.slf4j.Slf4j;
import org.sikuli.script.ImagePath;

@Slf4j
public class DSOAutomationApp {

    private static final SikuliCommands sikuli = SikuliCommands.build();

//    static {
//        ImagePath.add("../dso_1.sikuli");
//    }

    public static void main(String[] args) {
        log.info("App starting");

        DSOServices dSOAutomation = new DSOServices();

//        sikuli.existsLetsPlayButton();
        dSOAutomation.startDsoApp();
//        dSOAutomation.closeWelcomeDialog();
//        setROI(10, 115, 1290 - 10, 1047 - 115) // setROI(x, y, w, h)
//        focusApp()
        //dSOAutomation.findAllCollectables()

        // *** Geologen***
        dSOAutomation.prepareStarMenuForGeologicsNExplorers();
        dSOAutomation.launchAllHappyGeologics("ST", 6);
        //dSOAutomation.sleep(70);
        //dSOAutomation.launchAllHappyGeologics("KU", 6);
        //dSOAutomation.solveDailyQuest();
        //dSOAutomation.solveGuildQuest();
        //dSOAutomation.sleep();
        //dSOAutomation.launchAllHappyGeologics("GR", 6);

        //dSOAutomation.launchAllNormalGeologics("MA", 3);
        //dSOAutomation.launchAllConscientiousGeologics("MA", 2);
        //dSOAutomation.launchAllHappyGeologics("EI", 6);

        //dSOAutomation.openStarMenuForExplorer();
        // *** TODO Entdecker***
        // Sternmenu für Entdecker öffnen
        //dSOAutomation.launchAllBraveExplorer();        // Mutige
        //dSOAutomation.launchAllSuccessfulExplorer();    // Erfolgreiche
        //dSOAutomation.launchAllWildExplorer();          // Wilde
        //dSOAutomation.launchAllFearlessExplorer();      // Furchlose
        //dSOAutomation.launchAllNormalExplorer();
        // *** Todo Geologen***
        // ....

        log.info("App ends normally.");
    }
}
