package de.puettner.sikuli.dso.adv;

import de.puettner.sikuli.dso.commands.ui.MenuButton;
import org.sikuli.script.Pattern;

import static de.puettner.sikuli.dso.commands.ui.SikuliCommands.pattern;

public enum GeneralMenuButton implements MenuButton {

    Move(pattern("General-Move-Button.png").similar(0.90f)),
    Attack(pattern("General-Attack-Button.png").similar(0.95f)),
    ReleaseUnits(pattern("General-ReleaseUnits-Button.png").similar(0.90f)),
    PutBackToStarMenu(pattern("General-PutBackToStarMenu-Button.png").similar(0.90f)),
    RookieIcon(pattern("Armee-Rekruten.png").similar(0.90f).targetOffset(10, 10));

    public final Pattern pattern;

    GeneralMenuButton(Pattern pattern) {
        this.pattern = pattern;
    }

    public Pattern getPattern() {
        return pattern;
    }
}
