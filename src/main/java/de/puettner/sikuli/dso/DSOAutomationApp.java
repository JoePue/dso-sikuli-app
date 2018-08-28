package de.puettner.sikuli.dso;

import de.puettner.sikuli.dso.commands.os.WindowsPlatform;
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
        WindowsPlatform platform = new WindowsPlatform();
        //platform.maximizeBrowserWindow(); // must the first stmt !!!
        CommandBuilder cmdBuilder = CommandBuilder.build();
        SikuliCommands sikuli = cmdBuilder.buildIslandCommand();
        DSOServices dsoService = DSOServiceBuilder.build();
        try {
            for (String arg : args) {
                if ("firstDailyRun".equals(arg)) {
                    firstDailyRun(dsoService);
                } else if ("secondDailyRun".equals(arg)) {
                    secondDailyRun(dsoService);
                } else if ("standby".equals(arg)) {
                    platform.standby();
                }
            }
        } finally {
            platform.restoreBrowserWindow();
        }
        log.info("App ends normally.");
    }

    private static void firstDailyRun(DSOServices dsoService) {
        dsoService.startDsoApp();
        dsoService.closeWelcomeDialog();
        dsoService.highlightRegions();
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
        dsoService.buildAllMines();
        dsoService.findAllCollectables();

        // TODO Logging konfigurieren / fachliches Logging def. / Log-File
        // TODO Programmabstürze fixen (Ständig Endlosschleifen)
        // TODO Workaround für Programmabstürze finden
        // TODO Goldsuche impl.
        // TODO Kohlesuche impl. TODO Buffen Fkt. impl. für GoldTürm, Granitm., Gold, Eisen, (Stein, Marmor)
        // TODO implement programm arguments or something like profiles
        dsoService.launchAllHappyGeologics(MaterialType.GR, 6);

        dsoService.exitDso();
    }

    private static void secondDailyRun(DSOServices dsoService) {
        dsoService.startDsoApp();
        dsoService.closeWelcomeDialog();

        dsoService.prepareStarMenuForGold();
        dsoService.launchAllHappyGeologics(MaterialType.GO, 2);

        dsoService.prepareStarMenu();
        dsoService.launchAllHappyGeologics(MaterialType.GR, 4);
        dsoService.launchAllHappyGeologics(MaterialType.KO, 4);
        dsoService.launchAllConscientiousGeologics(MaterialType.GR, 2);

        dsoService.buildAllMines();

        dsoService.exitDso();
    }
}
