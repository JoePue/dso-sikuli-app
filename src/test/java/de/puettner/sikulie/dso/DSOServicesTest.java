package de.puettner.sikulie.dso;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.*;

public class DSOServicesTest {

    private DSOServices dsoServices = new DSOServices();

    @Before
    public void before() {
        dsoServices.switchToBrowser();
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
