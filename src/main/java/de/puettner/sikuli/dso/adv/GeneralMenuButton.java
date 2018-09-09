package de.puettner.sikuli.dso.adv;

import de.puettner.sikuli.dso.commands.ui.MenuButton;
import org.sikuli.script.Pattern;

import static de.puettner.sikuli.dso.commands.ui.SikuliCommands.pattern;

public enum GeneralMenuButton implements MenuButton {

    Move(pattern("General-Move-Button.png").similar(0.90)),
    Attack(pattern("General-Attack-Button.png").similar(0.95)),
    ReleaseUnits(pattern("General-ReleaseUnits-Button.png").similar(0.90)),
    
    RookieIcon(pattern("Armee-Rekruten.png").similar(0.90).targetOffset(10, 10));

    public final Pattern pattern;

    GeneralMenuButton(Pattern pattern) {
        this.pattern = pattern;
    }

    public Pattern getPattern() {
        return pattern;
    }
}
