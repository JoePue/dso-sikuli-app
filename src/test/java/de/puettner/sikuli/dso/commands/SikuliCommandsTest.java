package de.puettner.sikuli.dso.commands;

import org.junit.Before;
import org.junit.Test;
import org.sikuli.script.Commands;
import org.sikuli.script.Key;
import org.sikuli.script.Location;

import static java.awt.event.KeyEvent.VK_ENTER;

public class SikuliCommandsTest {

    private static final CommandBuilder cmBuilder = CommandBuilder.build();
    private final SikuliCommands sikuliCmd = cmBuilder.buildSikuliCommand();
    private final StarMenuCommands starMenu = cmBuilder.buildStarMenuCommands();

    @Before
    public void before() {
        sikuliCmd.switchToBrowser();
    }

    @Test
    public void openStarMenu() {
        starMenu.openStarMenu();
    }

    @Test
    public void exists() {
        System.out.println(sikuliCmd.exists("Ok-Button-0.png"));
        System.out.println(sikuliCmd.exists("Ok-Button-1.png"));
        System.out.println(sikuliCmd.exists("Ok-Button-2.png"));
    }

    @Test
    public void type() {
        sikuliCmd.type(Key.ESC);
    }

    @Test
    public void highlightAppRegions() {
        sikuliCmd.highlightAppRegions();
    }

    @Test
    public void paste() {
        Location location = Commands.click(new Location(100, 1000));
        sikuliCmd.type("Sikuli Test " + System.currentTimeMillis() + Key.ENTER); // java.lang.IllegalArgumentException: Invalid key code
        // wegen ":"
        sikuliCmd.paste("entdeck|kundsch|geolo");
        System.out.println(VK_ENTER);
    }

}
