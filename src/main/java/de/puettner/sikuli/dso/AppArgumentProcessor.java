package de.puettner.sikuli.dso;

import de.puettner.sikuli.dso.adv.AdventureBuilder;
import de.puettner.sikuli.dso.commands.os.WindowsPlatform;
import de.puettner.sikuli.dso.commands.ui.MenuBuilder;
import de.puettner.sikuli.dso.commands.ui.StarMenuFilter;
import lombok.extern.java.Log;
import org.sikuli.basics.Settings;

import static de.puettner.sikuli.dso.commands.ui.MaterialType.*;

// TODO Logging konfigurieren / fachliches Logging def. / Log-File
// TODO Buffen Fkt. impl. für GoldTürm, Granitm., Gold, Eisen, (Stein, Marmor)
@Log
public class AppArgumentProcessor {

    public final AppEnvironment appEnvironment;

    public AppArgumentProcessor() {
        this.appEnvironment = AppEnvironment.getInstance();
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
        WindowsPlatform platform = new WindowsPlatform();
        logSettings();
        MenuBuilder menuBuilder = MenuBuilder.build();

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

    private void firstDailyRun(DSOService dsoService) {
        dsoService.startDsoApp();
        // dsoService.highlightRegions();
        GeologicLaunchs geologicLaunchs = GeologicLaunchs.builder().build()
                .add(GeologicType.Happy, EI, 1, StarMenuFilter.GEO_1);
        dsoService.launchGeologics(geologicLaunchs);
        dsoService.buildAllMines(true);
        //dsoService.prepareStarMenu();
        dsoService.confirmSolvedQuest();
        dsoService.confirmNewQuest();
        // 10 Happy Geo / 3 Consic / 3 Normal
        geologicLaunchs = GeologicLaunchs.builder().build()
                .add(GeologicType.Happy, KU, 6)
                .add(GeologicType.Happy, ST, 4)
                .add(GeologicType.Normal, ST, 3)
                .add(GeologicType.Conscientious, ST, 2);
        dsoService.launchGeologics(geologicLaunchs);
        dsoService.launchAllExplorer();

        dsoService.fetchBookbinderItem();
        dsoService.solveDailyQuest();
        dsoService.solveGuildQuest();

        dsoService.findAllCollectables();
        dsoService.buildAllMines(false);

        // jetzt sollten alle Goes wieder verfügbar sein.
        geologicLaunchs = GeologicLaunchs.builder().build()
                .add(GeologicType.Happy, GO, 2, StarMenuFilter.EIGTH_PERCENT)
                .add(GeologicType.Happy, MA, 6, StarMenuFilter.GRANIT_GEOS)
                .add(GeologicType.Normal, KO, 1, StarMenuFilter.GEO_2)
                .add(GeologicType.Normal, GR, 1)
                .add(GeologicType.Normal, EI, 1)
                .add(GeologicType.Conscientious, GR, 3)
        ;
        dsoService.launchGeologics(geologicLaunchs);
        dsoService.buildAllMines(false);
        dsoService.fetchRewardMessages();
        dsoService.confirmNewQuest();
        dsoService.confirmSolvedQuest();
        dsoService.goToFirstSector();
    }

    private void secondDailyRun(DSOService dsoService) {
        dsoService.startDsoApp();
        dsoService.buildAllMines(true);
        dsoService.solveDailyQuest();
        dsoService.solveGuildQuest();
        dsoService.confirmNewQuest();
        dsoService.confirmSolvedQuest();
        dsoService.prepareStarMenu();

        // Ausgangspunkt: 6 verfügbare Geos
        GeologicLaunchs launchs = GeologicLaunchs.builder().build()
                .add(GeologicType.Happy, GR, 6, StarMenuFilter.GRANIT_GEOS)
                .add(GeologicType.Happy, EI, 1, StarMenuFilter.GEO_1)
                .add(GeologicType.Happy, KO, 1, StarMenuFilter.GEO_2)
                .add(GeologicType.Happy, EI, 2)
                .add(GeologicType.Conscientious, GR, 2);
        dsoService.launchGeologics(launchs);

        dsoService.buildAllMines(false);
        dsoService.findAllCollectables();
        dsoService.fetchBookbinderItem();
        dsoService.confirmNewQuest();
        dsoService.confirmSolvedQuest();
    }

    private void thirdDailyRun(DSOService dsoService) {
        dsoService.startDsoApp();

        dsoService.prepareStarMenuForGold();
        dsoService.launchGeologics(GeologicLaunchs.builder().build().add(GeologicType.Happy, GO, 2));

        dsoService.prepareStarMenu();
        GeologicLaunchs launchs = GeologicLaunchs.builder().build()
                .add(GeologicType.Happy, GR, 4)
                .add(GeologicType.Happy, KO, 4)
                .add(GeologicType.Conscientious, GR, 2);
        dsoService.launchGeologics(launchs);

        dsoService.buildAllMines(false);
        dsoService.findAllCollectables();
        dsoService.exitDso();
    }

}
