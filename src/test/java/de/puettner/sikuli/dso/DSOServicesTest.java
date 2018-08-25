package de.puettner.sikuli.dso;

import de.puettner.sikuli.dso.commands.ui.MaterialType;
import org.junit.Before;
import org.junit.Test;

public class DSOServicesTest {

    private DSOServices dsoServices = new DSOServices();

    @Before
    public void before() {
        dsoServices.switchToBrowser();
    }

    @Test
    public void goToSector() {
        dsoServices.goToSector(10);
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
    public void launchAllNormalExplorer() {
        dsoServices.launchAllNormalExplorer();
    }

    @Test
    public void launchAllBraveExplorer() {
        dsoServices.launchAllBraveExplorer();
    }

    @Test
    public void launchAllHappyGeologics() {
        //        dsoServices.prepareStarMenu();
        dsoServices.launchAllHappyGeologics(MaterialType.GR, 4);
    }

    @Test
    public void launchAllSuccessfulExplorer() {
//        dsoServices.prepareStarMenu();
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
