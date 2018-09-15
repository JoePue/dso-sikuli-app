package de.puettner.sikuli.dso.adv;

import de.puettner.sikuli.dso.commands.ui.MenuBuilder;
import de.puettner.sikuli.dso.ui.commands.MenuTest;
import lombok.extern.java.Log;
import org.junit.Test;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Location;
import org.sikuli.script.Match;

import static de.puettner.sikuli.dso.adv.AttackUnit.*;

@Log
public class GeneralMenuTest extends MenuTest {

    private GeneralMenu menu = MenuBuilder.build().buildGeneralMenu();

    public GeneralMenuTest() {
        super();
    }

    @Test
    public void setupGeneralEnterInputFields() throws FindFailed {
        //        menu.highlightMenuRegion();
        Location location;
        Match match = islandCmds.find(GeneralMenuButton.RookieIcon.pattern, menu.getMenuRegion());
        location = new Location(match.x + 70, match.y + 20); // Reks
        match.hover(location);
        location = new Location(match.x + 190, match.y + 20); // BS
        match.hover(location);
        location = new Location(match.x + 320, match.y + 20); // Mil
        match.hover(location);

        // 2. Zeile
        location = new Location(match.x + 70, match.y + 75); // Cav
        match.hover(location);

        // 3 Zeile
        location = new Location(match.x + 70, match.y + 130); // AB
        match.hover(location);

    }

    @Test
    public void setupAttackUnits() throws FindFailed {
        AttackUnit[] units = {Rek(10), Bos(10), Cav(10), Lnb(10), Sol(10), Arm(10), Kan(10)};
        menu.setupAttackUnits(units);
    }
}
