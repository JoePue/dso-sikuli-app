package de.puettner.sikuli.dso.commands.ui;

import org.junit.Before;
import org.junit.Test;
import org.sikuli.script.Commands;
import org.sikuli.script.Key;
import org.sikuli.script.Location;

import java.util.Optional;

import static de.puettner.sikuli.dso.commands.ui.SikuliCommands.pattern;
import static java.awt.event.KeyEvent.VK_ENTER;

public class SikuliCommandsTest {

    private static final CommandBuilder cmdBuilder = CommandBuilder.build();
    private final IslandCommands islandCmds = cmdBuilder.buildIslandCommand();
    private final StarMenuCommands starMenu = cmdBuilder.buildStarMenuCommands();

    @Before
    public void before() {
        islandCmds.switchToBrowser();
    }

    @Test
    public void dragNdrop() {
        int[] sectors = {1, 2, 3};
        for (int sector : sectors) {
            islandCmds.type("" + sector, null);
            islandCmds.sleep(1);
            islandCmds.dragNdrop(200, -750);
        }

    }

    @Test
    public void openStarMenu() {
        starMenu.openStarMenu(Optional.empty());
    }

    @Test
    public void exists() {
        System.out.println(islandCmds.exists(pattern("Ok-Button-0.png")));
        System.out.println(islandCmds.exists(pattern("Ok-Button-1.png")));
        System.out.println(islandCmds.exists(pattern("Ok-Button-2.png")));
    }

    @Test
    public void type() {
        islandCmds.type(Key.ESC);
    }

    @Test
    public void paste() {
        Location location = Commands.click(new Location(100, 1000));
        islandCmds.type("Sikuli Test " + System.currentTimeMillis() + Key.ENTER); // java.lang.IllegalArgumentException: Invalid key code
        // wegen ":"
        islandCmds.paste("entdeck|kundsch|geolo");
        System.out.println(VK_ENTER);
    }

}
