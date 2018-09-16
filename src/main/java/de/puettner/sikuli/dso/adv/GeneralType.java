package de.puettner.sikuli.dso.adv;

import de.puettner.sikuli.dso.commands.ui.MenuButton;
import org.sikuli.script.Pattern;

import static de.puettner.sikuli.dso.commands.ui.SikuliCommands.pattern;

public enum GeneralType implements MenuButton {
    Anslem(pattern("Anslem.png").similar(0.90)),
    Nusala(pattern("Nusala.png").similar(0.90)),
    Vargus(pattern("Vargus.png").similar(0.90)),
    Dracul(pattern("Dracul.png").similar(0.90)),
    Generalmajor(pattern("Generalmajor.png").similar(0.90)),
    MdK(pattern("MdK.png").similar(0.90)),
    Mary(pattern("Mary.png").similar(0.90)),
    Veteran(pattern("Veteran.png").similar(0.90)),
    Sensenmann(pattern("Sensenmann.png").similar(0.90)),
    Kampfgestaehlte(pattern("Kampfgestaehlte.png").similar(0.90)),
    Verteidigungsmeister(pattern("Verteidigungsmeister.png").similar(0.90)),
    Taverni(pattern("TavernenGeneral.png").similar(0.90));

    public final Pattern pattern;

    GeneralType(Pattern pattern) {
        this.pattern = pattern;
    }

    public Pattern getPattern() {
        return pattern;
    }
}
