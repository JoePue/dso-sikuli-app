package de.puettner.sikuli.dso.adv;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.puettner.sikuli.dso.commands.ui.MenuButton;
import org.sikuli.script.Pattern;

@JsonDeserialize(using = AttackCampDeserializer.class)
public interface AttackCamp extends MenuButton {

    Pattern getPattern();

    //    NavigationPoint getNavigationPoint();

    //    Dimension getTargetDragDropOffset();
}
