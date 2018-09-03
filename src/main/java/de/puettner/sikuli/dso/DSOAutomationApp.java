package de.puettner.sikuli.dso;

import de.puettner.sikuli.dso.commands.os.WindowsPlatform;
import de.puettner.sikuli.dso.commands.ui.CommandBuilder;
import de.puettner.sikuli.dso.commands.ui.MaterialType;
import de.puettner.sikuli.dso.commands.ui.SikuliCommands;
import lombok.extern.java.Log;
import org.sikuli.basics.Settings;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Match;
import org.sikuli.script.Region;

import java.util.Iterator;
import java.util.logging.Level;

import static de.puettner.sikuli.dso.commands.ui.SikuliCommands.pattern;

@Log
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
        DSOService dsoService = DSOServiceBuilder.build();
        try {
            for (String arg : args) {
                dsoService.switchToBrowser();
                if ("firstDailyRun".equals(arg)) {
                    firstDailyRun(dsoService);
                } else if ("secondDailyRun".equals(arg)) {
                    secondDailyRun(dsoService);
                } else if ("launchAllExplorer".equals(arg)) {
                    dsoService.launchAllExplorer();
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
                    GeologicLaunchs launch = GeologicLaunchs.builder().build().add(GeologicType.Happy, MaterialType.valueOf
                            (args[1]), Integer.valueOf(args[1]));
                    dsoService.launchGeologics(launch);
                } else if ("launchAllNormalGeologics".equals(arg)) {
                    GeologicLaunchs launch = GeologicLaunchs.builder().build().add(GeologicType.Normal, MaterialType.valueOf
                            (args[1]), Integer.valueOf(args[1]));
                    dsoService.launchGeologics(launch);
                } else if ("launchAllConscientiousGeologics".equals(arg)) {
                    GeologicLaunchs launch = GeologicLaunchs.builder().build().add(GeologicType.Conscientious, MaterialType.valueOf
                            (args[1]), Integer.valueOf(args[1]));
                    dsoService.launchGeologics(launch);
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
                    log.log(Level.WARNING, "Unknown argument: " + arg);
                }
            }
        } finally {
            platform.restoreBrowserWindow();
        }
        log.info("App ends normally.");
        dsoService.sleep(5000);
        // Currently some Sikuli-Threads prevent termination of the java process
        System.exit(0);
    }

    private static void firstDailyRun(DSOService dsoService) {
        dsoService.startDsoApp();
        dsoService.closeWelcomeDialog();
        dsoService.highlightRegions();
        dsoService.prepareStarMenu();
        dsoService.buildCopperMines(3);

        GeologicLaunchs launchs = GeologicLaunchs.builder().build()
                .add(GeologicType.Happy, MaterialType.KU, 6)
                .add(GeologicType.Happy, MaterialType.ST, 2)
                .add(GeologicType.Normal, MaterialType.ST, 3)
                .add(GeologicType.Conscientious, MaterialType.ST, 2)
                .add(GeologicType.Happy, MaterialType.EI, 8)
                .add(GeologicType.Normal, MaterialType.MA, 3)
                .add(GeologicType.Conscientious, MaterialType.MA, 2);
        dsoService.launchGeologics(launchs);
        dsoService.launchAllExplorer();

        // *** So nun 30min verbrauchen ***

        dsoService.launchAllExplorer();

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
        dsoService.launchGeologics(GeologicLaunchs.builder().build().add(GeologicType.Happy, MaterialType.GR, 6));

        dsoService.exitDso();
    }

    private static void secondDailyRun(DSOService dsoService) {
        dsoService.startDsoApp();
        dsoService.closeWelcomeDialog();

        dsoService.prepareStarMenuForGold();
        dsoService.launchGeologics(GeologicLaunchs.builder().build().add(GeologicType.Happy, MaterialType.GO, 2));

        dsoService.prepareStarMenu();
        GeologicLaunchs launchs = GeologicLaunchs.builder().build()
                .add(GeologicType.Happy, MaterialType.GR, 4)
                .add(GeologicType.Happy, MaterialType.KO, 4)
                .add(GeologicType.Conscientious, MaterialType.GR, 2);
        dsoService.launchGeologics(launchs);

        dsoService.buildAllMines();

        dsoService.exitDso();
    }

    private static int getBuildQueueSize(DSOService dsoService) {
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
