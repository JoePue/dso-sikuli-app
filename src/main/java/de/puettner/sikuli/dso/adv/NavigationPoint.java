package de.puettner.sikuli.dso.adv;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.puettner.sikuli.dso.commands.ui.MenuButton;

@JsonDeserialize(using = NavigationPointDeserializer.class)
public interface NavigationPoint extends MenuButton {
    Integer getId();
}
