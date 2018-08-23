package de.puettner.sikulie.dso;

import org.junit.Before;
import org.junit.Test;

public class DSOServicesTest {

    private DSOServices dsoServices = new DSOServices();

    @Before
    public void before() {
        dsoServices.switchToBrowser();
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
        dsoServices.launchAllHappyGeologics("ST", 7);
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
