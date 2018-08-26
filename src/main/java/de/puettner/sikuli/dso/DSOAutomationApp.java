package de.puettner.sikuli.dso;

import de.puettner.sikuli.dso.commands.ui.CommandBuilder;
import de.puettner.sikuli.dso.commands.ui.MaterialType;
import de.puettner.sikuli.dso.commands.ui.SikuliCommands;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DSOAutomationApp {

    /*
     * *** Inventar ***                 *** Vorkommen ***
     * ================                 ================
     * HappyGeologics: 10               7 Steinvork. / 8 Marmorvk. / 6 Granitvk.
     * NormalGeologics: 3               6 Kupfervk. / 19 Eisenvorkommen / Titan
     * ConscientiousGeologics: 2        9 Goldvork. / 6 Kohlevork. / Salpeter
     */
    public static void main(String[] args) {
        log.info("App starting");
        CommandBuilder cmdBuilder = CommandBuilder.build();
        SikuliCommands sikuli = cmdBuilder.buildIslandCommand();

        DSOServices dsoService = new DSOServices();

        dsoService.startDsoApp();
        dsoService.closeWelcomeDialog();
        dsoService.prepareStarMenu();

        dsoService.buildCopperMines(3);
        dsoService.launchAllHappyGeologics(MaterialType.KU, 6);
        dsoService.launchAllHappyGeologics(MaterialType.ST, 2);
        dsoService.launchAllNormalGeologics(MaterialType.ST, 3);
        dsoService.launchAllConscientiousGeologics(MaterialType.ST, 2);
        // jetzt sollte alle schnellen Geo's wieder verfügbar sein
        dsoService.launchAllHappyGeologics(MaterialType.EI, 8);
        // TODO JPU dsoService.launchAllHappyGeologics(MaterialType.GO, 2);
        dsoService.launchAllNormalGeologics(MaterialType.MA, 3);
        dsoService.launchAllConscientiousGeologics(MaterialType.MA, 2);

        // *** So nun 30min verbrauchen ***

        dsoService.launchAllBraveExplorer();        // Mutige
        dsoService.launchAllSuccessfulExplorer();   // Erfolgreiche
        dsoService.launchAllWildExplorer();         // Wilde
        dsoService.launchAllFearlessExplorer();     // Furchlose
        dsoService.launchAllNormalExplorer();

        dsoService.fetchBookbinderItem();
        dsoService.solveDailyQuest();
        dsoService.solveGuildQuest();
        //dsoService.findAllCollectables()

        // TODO Minen buffen Fkt. impl.
        // TODO Buffen Fkt. impl. für GoldTürm, Granitm., Gold, Eisen, (Stein, Marmor)
        // TODO standby-Cmd impl.
        // TODO Goldsuche
        dsoService.launchAllHappyGeologics(MaterialType.GR, 6);

        dsoService.exitDso();
        log.info("App ends normally.");
    }
}
