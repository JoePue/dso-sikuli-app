package de.puettner.sikuli.dso;

import de.puettner.sikuli.dso.commands.os.WindowsPlatform;
import de.puettner.sikuli.dso.commands.ui.CommandBuilder;
import de.puettner.sikuli.dso.commands.ui.MaterialType;
import de.puettner.sikuli.dso.commands.ui.SikuliCommands;
import lombok.extern.slf4j.Slf4j;
import org.sikuli.basics.Settings;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Match;
import org.sikuli.script.Region;

import java.util.Iterator;

import static de.puettner.sikuli.dso.commands.ui.SikuliCommands.pattern;

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
        log.info("AutoWaitTimeout: " + Settings.AutoWaitTimeout);
        //platform.maximizeBrowserWindow(); // must the first stmt !!!
        CommandBuilder cmdBuilder = CommandBuilder.build();
        SikuliCommands sikuli = cmdBuilder.buildIslandCommand();
        DSOServices dsoService = DSOServiceBuilder.build();
        try {
            for (String arg : args) {
                dsoService.switchToBrowser();
                if ("firstDailyRun".equals(arg)) {
                    firstDailyRun(dsoService);
                } else if ("secondDailyRun".equals(arg)) {
                    secondDailyRun(dsoService);
                } else if ("launchAllExplorer".equals(arg)) {
                    launchAllExplorer(dsoService);
                } else if ("launchAllExplorer".equals(arg)) {
                    dsoService.findAllCollectables();
                } else if ("getBuildQueueSize".equals(arg)) {
                    getBuildQueueSize(dsoService);
                } else if ("buildAllMines".equals(arg)) {
                    dsoService.buildAllMines();
                } else if ("standby".equals(arg)) {
                    platform.standby();
                } else if ("closeWelcomeDialog".equals(arg)) {
                    dsoService.closeWelcomeDialog();
                } else if ("highlightRegions".equals(arg)) {
                    dsoService.highlightRegions();
                } else if ("prepareStarMenu".equals(arg)) {
                    dsoService.prepareStarMenu();
                } else if ("buildCopperMines".equals(arg)) {
                    dsoService.buildCopperMines(Integer.valueOf(args[1]));
                } else if ("launchAllHappyGeologics".equals(arg)) {
                    dsoService.launchAllHappyGeologics(MaterialType.valueOf(args[1]), Integer.valueOf(args[1]));
                } else if ("launchAllNormalGeologics".equals(arg)) {
                    dsoService.launchAllNormalGeologics(MaterialType.valueOf(args[1]), Integer.valueOf(args[1]));
                } else if ("launchAllConscientiousGeologics".equals(arg)) {
                    dsoService.launchAllConscientiousGeologics(MaterialType.valueOf(args[1]), Integer.valueOf(args[1]));
                } else if ("launchAllExplorer".equals(arg)) {
                    launchAllExplorer(dsoService);
                } else if ("fetchBookbinderItem".equals(arg)) {
                    dsoService.fetchBookbinderItem();
                } else if ("solveDailyQuest".equals(arg)) {
                    dsoService.solveDailyQuest();
                } else if ("solveGuildQuest".equals(arg)) {
                    dsoService.solveGuildQuest();
                } else if ("findAllCollectables".equals(arg)) {
                    dsoService.findAllCollectables();
                } else if ("exitDso".equals(arg)) {
                    dsoService.exitDso();
                } else {
                    log.warn("Unknown argument: " + arg);
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

        launchAllExplorer(dsoService);

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

    private static void launchAllExplorer(DSOServices dsoService) {
        dsoService.launchAllBraveExplorer();        // Mutige
        dsoService.launchAllSuccessfulExplorer();   // Erfolgreiche
        dsoService.launchAllWildExplorer();         // Wilde
        dsoService.launchAllFearlessExplorer();     // Furchlose
        dsoService.launchAllNormalExplorer();
    }

    private static int getBuildQueueSize(DSOServices dsoService) {
        dsoService.switchToBrowser();
        int counter = 0;
        Region searchRegion = Region.create(1213, 115, 150, 400);
        Iterator<Match> it = null;
        while (true) {
            try {
                it = searchRegion.findAll(pattern("BuildQueueEntry.png").similar(0.90f));
                while (it.hasNext()) {
                    counter++;
                }
            } catch (FindFailed findFailed) {
                findFailed.printStackTrace();
            }
        }
    }
}
