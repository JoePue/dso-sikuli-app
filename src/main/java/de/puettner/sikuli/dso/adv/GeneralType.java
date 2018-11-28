package de.puettner.sikuli.dso.adv;

import de.puettner.sikuli.dso.commands.ui.MenuButton;
import org.sikuli.script.Pattern;

import static de.puettner.sikuli.dso.commands.ui.SikuliCommands.pattern;

public enum GeneralType implements MenuButton {
    Anslem(pattern("Anslem.png").similar(0.98f)),
    Nusala(pattern("Nusala.png").similar(0.98f)),
    Vargus(pattern("Vargus.png").similar(0.90f)),
    Dracul(pattern("Dracul.png").similar(0.90f)),
    Generalmajor(pattern("Generalmajor.png").similar(0.98f)),
    MdK(pattern("MdK.png").similar(0.98f)),
    Mary(pattern("Mary.png").similar(0.98f)),
    Veteran(pattern("Veteran.png").similar(0.98f)),
    Sensenmann(pattern("Sensenmann.png").similar(0.98f)),
    Kampfgestaehlte(pattern("Kampfgestaehlte.png").similar(0.98f)),
    Verteidigungsmeister(pattern("Verteidigungsmeister.png").similar(0.98f)),
    Taverni(pattern("TavernenGeneral.png").similar(0.98f)),
    Sani(pattern("SaniGeneral.png").similar(0.98f));

    public final Pattern pattern;

    GeneralType(Pattern pattern) {
        this.pattern = pattern;
    }

    public Pattern getPattern() {
        return pattern;
    }
}
