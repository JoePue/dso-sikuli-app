package de.puettner.sikuli.dso;

import de.puettner.sikuli.dso.commands.CommandBuilder;
import de.puettner.sikuli.dso.commands.MaterialType;
import de.puettner.sikuli.dso.commands.SikuliCommands;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DSOAutomationApp {

    private static final SikuliCommands sikuli = CommandBuilder.buildSikuliCommand();

    public static void main(String[] args) {
        log.info("App starting");

        DSOServices dsoService = new DSOServices();

        dsoService.startDsoApp();
        dsoService.closeWelcomeDialog();
        //dsoService.findAllCollectables()
        // *** Geologen***
        dsoService.prepareStarMenu();
        dsoService.launchAllHappyGeologics(MaterialType.ST, 7);

        dsoService.launchAllHappyGeologics(MaterialType.KU, 6);
        dsoService.solveDailyQuest();
        dsoService.solveGuildQuest();

        dsoService.launchAllNormalGeologics(MaterialType.MA, 3);
        dsoService.launchAllHappyGeologics(MaterialType.GR, 6);
        dsoService.launchAllConscientiousGeologics(MaterialType.MA, 2);
        dsoService.launchAllHappyGeologics(MaterialType.EI, 4);

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

        dsoService.fetchBookbinderItem();

        dsoService.exitDso();
        log.info("App ends normally.");
    }
}
