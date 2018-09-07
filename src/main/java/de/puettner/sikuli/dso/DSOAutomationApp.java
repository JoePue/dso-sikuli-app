package de.puettner.sikuli.dso;

import de.puettner.sikuli.dso.commands.os.WindowsPlatform;
import de.puettner.sikuli.dso.commands.ui.MenuBuilder;
import de.puettner.sikuli.dso.commands.ui.SikuliCommands;
import lombok.extern.java.Log;
import org.sikuli.basics.Settings;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Match;
import org.sikuli.script.Region;

import java.util.Iterator;
import java.util.logging.Level;

import static de.puettner.sikuli.dso.commands.ui.MaterialType.*;
import static de.puettner.sikuli.dso.commands.ui.SikuliCommands.pattern;

// TODO Logging konfigurieren / fachliches Logging def. / Log-File
// TODO Programmabstürze fixen (Ständig Endlosschleifen)
// TODO Workaround für Programmabstürze finden
// TODO Goldsuche impl.
// TODO Kohlesuche impl. TODO Buffen Fkt. impl. für GoldTürm, Granitm., Gold, Eisen, (Stein, Marmor)
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
        logSettings();
        //platform.maximizeBrowserWindow(); // must the first stmt !!!
        MenuBuilder menuBuilder = MenuBuilder.build();
        SikuliCommands sikuli = menuBuilder.buildIslandCommand();
        DSOService dsoService = DSOServiceBuilder.build();
        try {
            for (String arg : args) {
                dsoService.focusBrowser();
                if ("firstDailyRun".equals(arg)) {
                    firstDailyRun(dsoService);
                } else if ("secondDailyRun".equals(arg)) {
                    secondDailyRun(dsoService);
                } else if ("thirdDailyRun".equals(arg)) {
                    thirdDailyRun(dsoService);
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
                    GeologicLaunchs launch = GeologicLaunchs.builder().build().add(GeologicType.Happy, valueOf
                            (args[1]), Integer.valueOf(args[1]));
                    dsoService.launchGeologics(launch);
                } else if ("launchAllNormalGeologics".equals(arg)) {
                    GeologicLaunchs launch = GeologicLaunchs.builder().build().add(GeologicType.Normal, valueOf
                            (args[1]), Integer.valueOf(args[1]));
                    dsoService.launchGeologics(launch);
                } else if ("launchAllConscientiousGeologics".equals(arg)) {
                    GeologicLaunchs launch = GeologicLaunchs.builder().build().add(GeologicType.Conscientious, valueOf
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
                } else if ("preventScreensaver".equals(arg)) {
                    dsoService.preventScreensaver();
                } else if ("maximizeBrowserWindow".equals(arg)) {
                    platform.maximizeBrowserWindow();
                } else {
                    log.log(Level.WARNING, "Unknown argument: " + arg);
                }
            }
        } finally {
            platform.restoreBrowserWindow();
        }
        log.info("App ends normally.");
        // Currently some Sikuli-Threads prevent termination of the java process
        System.exit(0);
    }

    private static void logSettings() {
        log.info("AutoWaitTimeout: " + Settings.AutoWaitTimeout);
        log.info("DelayValue: " + Settings.DelayValue);
        log.info("ThrowException: " + Settings.ThrowException);
    }

    private static void firstDailyRun(DSOService dsoService) {
        dsoService.startDsoApp();
        dsoService.closeWelcomeDialog();
        dsoService.highlightRegions();
        dsoService.buildAllMines();
        dsoService.prepareStarMenu();

        GeologicLaunchs launchs = GeologicLaunchs.builder().build()
                .add(GeologicType.Happy, KU, 6)
                .add(GeologicType.Happy, ST, 2)
                .add(GeologicType.Normal, ST, 3)
                .add(GeologicType.Conscientious, ST, 2)
                .add(GeologicType.Happy, EI, 8)
                .add(GeologicType.Normal, MA, 3)
                .add(GeologicType.Conscientious, MA, 2);
        dsoService.launchGeologics(launchs);
        dsoService.launchAllExplorer();

        dsoService.launchAllExplorer();
        dsoService.fetchBookbinderItem();
        dsoService.solveDailyQuest();
        dsoService.solveGuildQuest();
        dsoService.findAllCollectables();
        dsoService.buildAllMines();
        dsoService.exitDso();
    }

    private static void secondDailyRun(DSOService dsoService) {
        dsoService.startDsoApp();
        dsoService.closeWelcomeDialog();
        dsoService.highlightRegions();
        dsoService.buildAllMines();

        // Ausgangspunkt: 15 Geos verfügbar
        // 2x Gold
        // 3x 2,5h Granit
        // 3x 8h Granit
        dsoService.launchGeologics(GeologicLaunchs.builder().build().add(GeologicType.Happy, GR, 3));
        dsoService.launchGeologics(GeologicLaunchs.builder().build().add(GeologicType.Normal, GR, 1));
        dsoService.launchGeologics(GeologicLaunchs.builder().build().add(GeologicType.Conscientious, GR, 2));
        dsoService.launchGeologics(GeologicLaunchs.builder().build().add(GeologicType.Happy, GO, 2));

        dsoService.exitDso();
    }

    private static void thirdDailyRun(DSOService dsoService) {
        dsoService.startDsoApp();
        dsoService.closeWelcomeDialog();

        dsoService.prepareStarMenuForGold();
        dsoService.launchGeologics(GeologicLaunchs.builder().build().add(GeologicType.Happy, GO, 2));

        dsoService.prepareStarMenu();
        GeologicLaunchs launchs = GeologicLaunchs.builder().build()
                .add(GeologicType.Happy, GR, 4)
                .add(GeologicType.Happy, KO, 4)
                .add(GeologicType.Conscientious, GR, 2);
        dsoService.launchGeologics(launchs);

        dsoService.buildAllMines();

        dsoService.exitDso();
    }

    /**
     * Test method for sikuli bug.
     */
    private static int getBuildQueueSize(DSOService dsoService) {
        dsoService.focusBrowser();
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
