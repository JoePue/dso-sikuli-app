package de.puettner.sikulie.dso;

import org.junit.Before;
import org.junit.Test;
import org.sikuli.script.Commands;
import org.sikuli.script.Key;
import org.sikuli.script.Location;

import static java.awt.event.KeyEvent.VK_ENTER;

/**
 * Created by joerg.puettner on 19.08.2018.
 */
public class SikuliCommandsTest {

    private final SikuliCommands sikuliCmd = SikuliCommands.build();

    @Before
    public void before() {
        sikuliCmd.switchToBrowser();
    }

    @Test
    public void openStarMenu() {
        sikuliCmd.openStarMenu();
    }

    @Test
    public void test5() {
        System.out.println(sikuliCmd.exists("Ok-Button-0.png"));
        System.out.println(sikuliCmd.exists("Ok-Button-1.png"));
        System.out.println(sikuliCmd.exists("Ok-Button-2.png"));
    }

    @Test
    public void test4() {
        sikuliCmd.type(Key.ESC);
    }

    @Test
    public void highlightAppRegions() {
        sikuliCmd.highlightAppRegions();
    }

    @Test
    public void test2() {
        Location location = Commands.click(new Location(100, 1000));
        sikuliCmd.type("Sikuli Test " + System.currentTimeMillis() + Key.ENTER); // java.lang.IllegalArgumentException: Invalid key code
        // wegen ":"
        sikuliCmd.paste("entdeck|kundsch|geolo");
        System.out.println(VK_ENTER);
    }

}
