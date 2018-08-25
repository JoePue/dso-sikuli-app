package de.puettner.sikuli.dso.commands.ui;

import org.sikuli.script.Pattern;

import static de.puettner.sikuli.dso.commands.ui.SikuliCommands.pattern;

public enum StarMenuButtons implements MenuButton {
    ZOOM_ICON(pattern("zoom-icon.png").targetOffset(-43, -3)),
    HappyGeologic(pattern("HappyGeologic-icon.png").similar(0.80f)),
    NormalGeologic(pattern("NormalGeologic-icon.png").similar(0.80f)),
    ConscientiousGeologic(pattern("ConscientiousGeologic-icon.png").similar(0.80f)),
    StarMenuTitleImage(pattern("StarMenuTitleImage.png"));


    public final Pattern pattern;

    StarMenuButtons(Pattern pattern) {
        this.pattern = pattern;
    }

    public Pattern getPattern() {
        return pattern;
    }
}
