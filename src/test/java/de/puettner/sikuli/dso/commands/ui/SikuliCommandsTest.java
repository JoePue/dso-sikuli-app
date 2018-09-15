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

    private static final MenuBuilder menuBuilder = MenuBuilder.build();
    private final IslandCommands islandCmds = menuBuilder.buildIslandCommand();
    private final StarMenu starMenu = menuBuilder.buildStarMenuCommands();

    @Before
    public void before() {
        //        islandCmds.focusBrowser();
    }

    @Test
    public void dragNDrop() {
        int[] sectors = {1, 2, 3};
        for (int sector : sectors) {
            islandCmds.type("" + sector, null);
            islandCmds.sleep();
            islandCmds.dragDrop(200, 750);
        }
    }

    @Test
    public void openStarMenu() {
        starMenu.openStarMenu(Optional.of(StarMenuFilter.ENTDDECK_KUNDSCH_GEOLO));
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
        islandCmds.paste("TEST");
        System.out.println(VK_ENTER);
    }

}
