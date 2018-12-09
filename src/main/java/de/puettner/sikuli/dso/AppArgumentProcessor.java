package de.puettner.sikuli.dso;

import de.puettner.sikuli.dso.adv.AdventureBuilder;
import de.puettner.sikuli.dso.commands.os.WindowsPlatform;
import de.puettner.sikuli.dso.commands.ui.BookbinderMenuButtons;
import de.puettner.sikuli.dso.commands.ui.MenuBuilder;
import lombok.extern.java.Log;
import org.sikuli.basics.Settings;

import static de.puettner.sikuli.dso.commands.ui.MaterialType.valueOf;

// TODO Logging konfigurieren / fachliches Logging def. / Log-File
// TODO Buffen Fkt. impl. für GoldTürm, Granitm., Gold, Eisen, (Stein, Marmor)
@Log
public class AppArgumentProcessor {

    public final AppEnvironment appEnvironment;

    public AppArgumentProcessor() {
        this.appEnvironment = InstanceBuilder.buildAppEnvironment();
    }

    /*
     * *** Inventar ***                 *** Vorkommen ***
     * ================                 ================
     * HappyGeologics: 10               7 Steinvork. / 8 Marmorvk. / 6 Granitvk.
     * NormalGeologics: 3               6 Kupfervk. / 19 Eisenvorkommen / Titan
     * ConscientiousGeologics: 2        9 Goldvork. / 6 Kohlevork. / Salpeter
     */
    public void process(String[] args) {
        log.info("process()");
        logArgs(args);
        WindowsPlatform platform = WindowsPlatform.Builder.build();
        logSettings();
        MenuBuilder menuBuilder = MenuBuilder.build();

        DSOService dsoService = DSOServiceBuilder.build();
        try {
            for (String arg : args) {
                if (arg == null) {
                    continue;
                }
                dsoService.focusBrowser();
                if ("firstDailyRun".equals(arg)) {
                    HomeIslandRunConfigs.firstDailyRun(dsoService);
                } else if ("secondDailyRun".equals(arg)) {
                    HomeIslandRunConfigs.secondDailyRun(dsoService);
                } else if ("thirdDailyRun".equals(arg)) {
                    HomeIslandRunConfigs.thirdDailyRun(dsoService);
                } else if ("launchAllExplorer".equals(arg)) {
                    dsoService.launchAllExplorer();
                } else if ("launchAllExplorer".equals(arg)) {
                    dsoService.findAllCollectables();
                } else if ("buildAllMines".equals(arg)) {
                    dsoService.buildAllMines(false);
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
                    GeologicLaunches launch = GeologicLaunches.builder().build().add(GeologicType.Happy, valueOf
                            (args[1]), Integer.valueOf(args[1]));
                    dsoService.launchGeologics(launch);
                } else if ("launchAllNormalGeologics".equals(arg)) {
                    GeologicLaunches launch = GeologicLaunches.builder().build().add(GeologicType.Normal, valueOf
                            (args[1]), Integer.valueOf(args[1]));
                    dsoService.launchGeologics(launch);
                } else if ("launchAllConscientiousGeologics".equals(arg)) {
                    GeologicLaunches launch = GeologicLaunches.builder().build().add(GeologicType.Conscientious, valueOf
                            (args[1]), Integer.valueOf(args[1]));
                    dsoService.launchGeologics(launch);
                } else if ("fetchBookbinderItem".equals(arg)) {
                    dsoService.fetchBookbinderItem(BookbinderMenuButtons.Manusskript);
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
                } else if ("playBraveTailorAdventure".equals(arg)) {
                    AdventureBuilder.build().buildBraveTailorAdv().play();
                } else {
                    log.fine("Unknown argument: " + arg);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            log.info("App ends normally.");
            // Currently some Sikuli-Threads prevent termination of the java process
            System.exit(0);
        }
    }

    private void logArgs(String[] args) {
        log.info("Level: info");
        log.severe("Level: severe");
        log.fine("Level: fine");
        log.finer("Level: finer");
        log.finest("Level: finest");
        log.warning("Level: warning");
        log.config("Level: config");

        log.fine("args.length: " + args.length);
        for (String arg : args) {
            log.fine("[argument] " + arg);
        }
    }

    private void logSettings() {
        log.info("AutoWaitTimeout: " + Settings.AutoWaitTimeout);
        log.info("DelayValue: " + Settings.DelayValue);
        log.info("ThrowException: " + Settings.ThrowException);
    }

}
