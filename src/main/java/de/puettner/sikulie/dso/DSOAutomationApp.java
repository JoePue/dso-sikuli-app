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

        DSOServices dsoService = new DSOServices();

//        dsoService.startDsoApp();
//        dsoService.closeWelcomeDialog();
//        //dsoService.findAllCollectables()
//        // *** Geologen***
//        dsoService.prepareStarMenuForGeologicsNExplorers();
//        dsoService.launchAllHappyGeologics("ST", 7);
//
//        dsoService.launchAllHappyGeologics("KU", 6);
//        dsoService.solveDailyQuest();
//        dsoService.solveGuildQuest();
//
//        dsoService.launchAllNormalGeologics("MA", 3);
//        dsoService.launchAllHappyGeologics("GR", 6);
//        dsoService.launchAllConscientiousGeologics("MA", 2);
//        dsoService.launchAllHappyGeologics("EI", 4);

        //dsoService.openStarMenuForExplorer();
        // *** TODO Entdecker***
        // Sternmenu für Entdecker öffnen
        dsoService.launchAllBraveExplorer();        // Mutige
        dsoService.launchAllSuccessfulExplorer();    // Erfolgreiche
        dsoService.launchAllWildExplorer();          // Wilde
        dsoService.launchAllFearlessExplorer();      // Furchlose
        dsoService.launchAllNormalExplorer();
        // *** Todo Geologen***
        // ....
        dsoService.exitDso();
        log.info("App ends normally.");
    }
}
