package de.puettner.sikuli.dso.ui.commands;

import de.puettner.sikuli.dso.GeologicLaunch;
import de.puettner.sikuli.dso.GeologicType;
import de.puettner.sikuli.dso.commands.ui.*;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

public class StarMenuTest extends MenuTest {

    private final StarMenu starMenu = MenuBuilder.build().buildStarMenuCommands();

    @Before
    public void before() {
        sikuliCmd.focusBrowser();
    }

    @Test
    public void openStarMenu() {
        // starMenu.openStarMenu(Optional.empty());
        starMenu.openStarMenu(Optional.of(StarMenuFilter.EIGTH_PERCENT));
    }

    @Test
    public void openMessageBox() {
        starMenu.openMessageBox();
    }

    @Test
    public void launchAllGeologicsByImage() {
        StarMenuButtons starMenuButton = StarMenuButtons.HappyGeologic;
        GeologicLaunch launch = new GeologicLaunch(GeologicType.Happy, MaterialType.GO, 1, StarMenuFilter.EIGTH_PERCENT);
        starMenu.launchAllGeologicsByImage(starMenuButton, launch.getMaterial(), launch.getLaunchLimit(), launch.getFilter());
    }

}
