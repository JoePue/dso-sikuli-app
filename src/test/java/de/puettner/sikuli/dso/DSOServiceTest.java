package de.puettner.sikuli.dso;

import de.puettner.sikuli.dso.commands.ui.MaterialType;
import org.junit.Before;
import org.junit.Test;

import static de.puettner.sikuli.dso.commands.ui.MaterialType.GR;

public class DSOServiceTest extends BaseServiceTest {

    private DSOService dsoService = DSOServiceBuilder.build();

    @Before
    public void before() {
        dsoService.focusBrowser();
    }

    @Test
    public void prepareStarMenu() {
        dsoService.prepareStarMenu();
    }

    @Test
    public void buildAllMines() {
        dsoService.buildAllMines(true);
    }

    @Test
    public void findAllCollectables() {
        dsoService.findAllCollectables();
    }

    @Test
    public void buildCopperMines() {
        System.out.println(dsoService.buildCopperMines(3));
    }

    @Test
    public void buildGoldMines() {
        System.out.println(dsoService.buildGoldMines(1));
    }

    @Test
    public void buildColeMines() {
        System.out.println(dsoService.buildColeMines(3));
    }

    @Test
    public void buildIronMines() {
        int count = 0;
        count = dsoService.buildIronMines(3);
        System.out.println("Build " + count + " iron mines");
    }

    @Test
    public void launchAllExplorer() {
        dsoService.launchAllExplorer();
    }

    @Test
    public void launchAllNormalExplorer() {
        dsoService.launchAllNormalExplorer();
    }

    @Test
    public void launchAllBraveExplorer() {
        dsoService.launchAllBraveExplorer();
    }

    @Test
    public void launchAllGeologics() {
        GeologicLaunches launches = GeologicLaunches.builder().build()
                .add(GeologicType.Conscientious, GR, 3);
        dsoService.launchGeologics(launches);
    }

    @Test
    public void launchAllHappyGeologics() {
        GeologicLaunches.builder().build().add(GeologicType.Happy, MaterialType.ST, 1);
    }

    @Test
    public void launchAllSuccessfulExplorer() {
        dsoService.launchAllSuccessfulExplorer();
    }

    @Test
    public void launchAllFearlessExplorer() {
        dsoService.launchAllFearlessExplorer();
    }

    @Test
    public void closeWelcomeDialog() {
        dsoService.closeWelcomeDialog();
    }

    @Test
    public void fetchBookbinderItem() {
        dsoService.fetchBookbinderItem();
    }

    @Test
    public void preventScreensaver() {
        dsoService.preventScreensaver();
    }

    @Test
    public void startDsoApp() {
        dsoService.startDsoApp();
    }

    @Test
    public void solveDailyQuest() {
        dsoService.solveDailyQuest();
    }

    @Test
    public void solveGuildQuest() {
        dsoService.solveGuildQuest();
    }

    @Test
    public void closeChat() {
        dsoService.closeChat();
    }

    @Test
    public void fetchRewardMessages() {
        dsoService.fetchRewardMessages();
    }

    @Test
    public void confirmSolvedQuest() {
        dsoService.confirmSolvedQuest();
    }

    @Test
    public void highlightRegions() {
        dsoService.highlightRegions();
    }

}
