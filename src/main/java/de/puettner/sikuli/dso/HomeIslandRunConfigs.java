package de.puettner.sikuli.dso;

import de.puettner.sikuli.dso.commands.ui.BookbinderMenuButtons;
import de.puettner.sikuli.dso.commands.ui.StarMenuFilter;

import static de.puettner.sikuli.dso.commands.ui.MaterialType.*;

public class HomeIslandRunConfigs {

    private static BookbinderMenuButtons bookType = BookbinderMenuButtons.Manusskript;

    public static void firstDailyRun(DSOService dsoService) {
        dsoService.startDsoApp();
        GeologicLaunches geologicLaunches = GeologicLaunches.builder().build()
                .add(GeologicType.Happy, EI, 1, StarMenuFilter.GEO_1);
        dsoService.launchGeologics(geologicLaunches);
        dsoService.buildAllMines(true);

        dsoService.confirmSolvedQuest();
        dsoService.confirmNewQuest();
        // 10 Happy Geo / 3 Consic / 3 Normal
        geologicLaunches = GeologicLaunches.builder().build()
                .add(GeologicType.Happy, KU, 6)
                .add(GeologicType.Happy, ST, 4)
                .add(GeologicType.Normal, ST, 3)
                .add(GeologicType.Conscientious, ST, 3);
        dsoService.launchGeologics(geologicLaunches);
        dsoService.launchAllExplorer();

        dsoService.fetchBookbinderItem(bookType);
        dsoService.solveDailyQuest();
        dsoService.solveGuildQuest();

        dsoService.findAllCollectables();
        dsoService.buildAllMines(false);

        // jetzt sollten alle Goes wieder verfügbar sein.
        geologicLaunches = GeologicLaunches.builder().build()
                .add(GeologicType.Happy, GO, 2, StarMenuFilter.EIGTH_PERCENT)
                .add(GeologicType.Happy, MA, 6, StarMenuFilter.GRANIT_GEOS)
                .add(GeologicType.Normal, KO, 1, StarMenuFilter.GEO_2)
                .add(GeologicType.Normal, GR, 1)
                .add(GeologicType.Normal, EI, 1)
                .add(GeologicType.Conscientious, GR, 3)
        ;
        dsoService.launchGeologics(geologicLaunches);

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
        GeologicLaunches launches = GeologicLaunches.builder().build()
                .add(GeologicType.Happy, GR, 6, StarMenuFilter.GRANIT_GEOS)
                .add(GeologicType.Happy, EI, 1, StarMenuFilter.GEO_1)
                .add(GeologicType.Happy, KO, 1, StarMenuFilter.GEO_2)
                .add(GeologicType.Happy, EI, 2)
                .add(GeologicType.Conscientious, GR, 2);
        dsoService.launchGeologics(launches);

        dsoService.buildAllMines(false);
        dsoService.findAllCollectables();
        dsoService.fetchBookbinderItem(bookType);
        dsoService.confirmNewQuest();
        dsoService.confirmSolvedQuest();
    }

    public static void thirdDailyRun(DSOService dsoService) {
        dsoService.startDsoApp();

        dsoService.prepareStarMenuForGold();
        dsoService.launchGeologics(GeologicLaunches.builder().build().add(GeologicType.Happy, GO, 2));

        dsoService.prepareStarMenu();
        GeologicLaunches launches = GeologicLaunches.builder().build()
                .add(GeologicType.Happy, GR, 4)
                .add(GeologicType.Happy, KO, 4)
                .add(GeologicType.Conscientious, GR, 2);
        dsoService.launchGeologics(launches);

        dsoService.buildAllMines(false);
        dsoService.findAllCollectables();
    }
}
