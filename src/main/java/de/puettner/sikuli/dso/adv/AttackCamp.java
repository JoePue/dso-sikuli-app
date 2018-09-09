package de.puettner.sikuli.dso.adv;

import de.puettner.sikuli.dso.commands.ui.MenuButton;
import org.sikuli.script.Location;
import org.sikuli.script.Pattern;

public interface AttackCamp extends MenuButton {
    Pattern getPattern();

    Location getDragNDrop();
}
