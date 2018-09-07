package de.puettner.sikuli.dso;

import de.puettner.sikuli.dso.commands.ui.MaterialType;
import de.puettner.sikuli.dso.commands.ui.Sector;
import org.junit.Before;
import org.junit.Test;

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
        dsoService.buildAllMines();
        dsoService.exitDso();
    }

    @Test
    public void findAllCollectables() {
        dsoService.findAllCollectables();
    }

    @Test
    public void goToSector() {
        dsoService.goToSector(Sector.S10);
    }

    @Test
    public void buildCopperMines() {
        System.out.println(dsoService.buildCopperMines(3));
    }

    @Test
    public void buildGoldMines() {
        System.out.println(dsoService.buildGoldMines(3));
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
        //        dsoService.buildAllMines();
        GeologicLaunchs launchs = GeologicLaunchs.builder().build()
                .add(GeologicType.Happy, MaterialType.KU, 3)
                .add(GeologicType.Normal, MaterialType.ST, 3)
                .add(GeologicType.Happy, MaterialType.GR, 3)
                .add(GeologicType.Normal, MaterialType.MA, 3)
                .add(GeologicType.Conscientious, MaterialType.EI, 2)
                .add(GeologicType.Happy, MaterialType.KO, 1)
                .add(GeologicType.Happy, MaterialType.EI, 5);
        dsoService.launchGeologics(launchs);
        //        dsoService.buildAllMines();
        // dsoService.exitDso();
        // windowsPlatform.standby();
    }

    @Test
    public void launchAllHappyGeologics() {
        GeologicLaunchs.builder().build().add(GeologicType.Happy, MaterialType.ST, 1);
    }

    @Test
    public void launchAllSuccessfulExplorer() {
        dsoService.launchAllSuccessfulExplorer();
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
}
