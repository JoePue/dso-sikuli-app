package de.puettner.sikuli.dso;

import de.puettner.sikuli.dso.commands.ui.CommandBuilder;
import de.puettner.sikuli.dso.commands.ui.MaterialType;
import de.puettner.sikuli.dso.commands.ui.SikuliCommands;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DSOAutomationApp {

    public static void main(String[] args) {
        log.info("App starting");
        CommandBuilder cmdBuilder = CommandBuilder.build();
        SikuliCommands sikuli = cmdBuilder.buildSikuliCommand();

        DSOServices dsoService = new DSOServices();

        dsoService.startDsoApp();
        dsoService.closeWelcomeDialog();
        //dsoService.findAllCollectables()

        dsoService.prepareStarMenu();
        dsoService.launchAllHappyGeologics(MaterialType.ST, 7);
        dsoService.launchAllHappyGeologics(MaterialType.KU, 6);

        dsoService.launchAllNormalGeologics(MaterialType.MA, 3);
        dsoService.launchAllHappyGeologics(MaterialType.EI, 4);
        dsoService.launchAllConscientiousGeologics(MaterialType.MA, 2);

        dsoService.launchAllBraveExplorer();        // Mutige
        dsoService.launchAllSuccessfulExplorer();   // Erfolgreiche
        dsoService.launchAllWildExplorer();         // Wilde
        dsoService.launchAllFearlessExplorer();     // Furchlose
        dsoService.launchAllNormalExplorer();

        dsoService.fetchBookbinderItem();
        dsoService.solveDailyQuest();
        dsoService.solveGuildQuest();

        dsoService.launchAllHappyGeologics(MaterialType.GR, 6);

        dsoService.exitDso();
        log.info("App ends normally.");
    }
}
