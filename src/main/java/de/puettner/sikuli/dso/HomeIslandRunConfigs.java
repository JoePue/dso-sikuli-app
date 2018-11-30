package de.puettner.sikuli.dso;

import de.puettner.sikuli.dso.commands.ui.StarMenuFilter;

import static de.puettner.sikuli.dso.commands.ui.MaterialType.*;

public class HomeIslandRunConfigs {

    public static void firstDailyRun(DSOService dsoService) {
        dsoService.startDsoApp();
        GeologicLaunchs geologicLaunchs = GeologicLaunchs.builder().build()
                .add(GeologicType.Happy, EI, 1, StarMenuFilter.GEO_1);
        dsoService.launchGeologics(geologicLaunchs);
        dsoService.buildAllMines(true);

        dsoService.confirmSolvedQuest();
        dsoService.confirmNewQuest();
        // 10 Happy Geo / 3 Consic / 3 Normal
        geologicLaunchs = GeologicLaunchs.builder().build()
                .add(GeologicType.Happy, KU, 6)
                .add(GeologicType.Happy, ST, 4)
                .add(GeologicType.Normal, ST, 3)
                .add(GeologicType.Conscientious, ST, 3);
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

    public static void secondDailyRun(DSOService dsoService) {
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

    public static void thirdDailyRun(DSOService dsoService) {
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
