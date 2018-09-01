package de.puettner.sikuli.dso;

import de.puettner.sikuli.dso.commands.ui.MaterialType;
import de.puettner.sikuli.dso.commands.ui.Sector;
import org.junit.Before;
import org.junit.Test;

public class DSOServicesTest extends BaseServiceTest {

    private DSOServices dsoServices = DSOServiceBuilder.build();

    @Before
    public void before() {
        dsoServices.switchToBrowser();
    }

    @Test
    public void buildAllMines() {
        dsoServices.buildAllMines();
        dsoServices.exitDso();
    }

    @Test
    public void findAllCollectables() {
        dsoServices.findAllCollectables();
    }

    @Test
    public void goToSector() {
        dsoServices.goToSector(Sector.S10);
    }

    @Test
    public void buildCopperMines() {
        System.out.println(dsoServices.buildCopperMines(3));
    }

    @Test
    public void buildGoldMines() {
        System.out.println(dsoServices.buildGoldMines(3));
    }

    @Test
    public void buildColeMines() {
        System.out.println(dsoServices.buildColeMines(3));
    }

    @Test
    public void buildIronMines() {
        //        do {
        int count = 0;
        count = dsoServices.buildIronMines(3);
            System.out.println("Build " + count + " iron mines");
        //            if (count == 0) {
        //                break;
        //            }
        //            dsoServices.sleep(500);
        //        } while (true);
        //        System.out.println();
    }

    @Test
    public void launchAllExplorer() {
        dsoServices.launchAllExplorer();
    }

    @Test
    public void launchAllNormalExplorer() {
        dsoServices.launchAllNormalExplorer();
    }

    @Test
    public void launchAllBraveExplorer() {
        dsoServices.launchAllBraveExplorer();
    }

    @Test
    public void launchAllHappyGeologics() {
        //        dsoServices.buildAllMines();
        dsoServices.launchAllHappyGeologics(MaterialType.KU, 3);
        dsoServices.launchAllNormalGeologics(MaterialType.ST, 3);

        dsoServices.launchAllHappyGeologics(MaterialType.GR, 3);
        dsoServices.launchAllNormalGeologics(MaterialType.MA, 3);
        dsoServices.launchAllConscientiousGeologics(MaterialType.EI, 2);
        dsoServices.launchAllHappyGeologics(MaterialType.KO, 1);
        dsoServices.launchAllHappyGeologics(MaterialType.EI, 5);
        //        dsoServices.buildAllMines();
        // dsoServices.exitDso();
        // windowsPlatform.standby();
    }

    @Test
    public void launchAllHappyGeologics_SingleRun() {
        dsoServices.launchAllHappyGeologics(MaterialType.ST, 1);
    }

    @Test
    public void launchAllSuccessfulExplorer() {
        dsoServices.launchAllSuccessfulExplorer();    // Erfolgreiche
    }

    @Test
    public void closeWelcomeDialog() {
        dsoServices.closeWelcomeDialog();
    }

    @Test
    public void fetchBookbinderItem() {
        dsoServices.fetchBookbinderItem();
    }
}
