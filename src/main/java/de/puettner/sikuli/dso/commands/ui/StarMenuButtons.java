package de.puettner.sikuli.dso.commands.ui;

import org.sikuli.script.Pattern;

import static de.puettner.sikuli.dso.commands.ui.SikuliCommands.pattern;

public enum StarMenuButtons implements MenuButton {
    ZOOM_ICON(pattern("zoom-icon.png").targetOffset(-43, -3)),
    HappyGeologic(pattern("HappyGeologic-icon.png").similar(0.85f)),
    NormalGeologic(pattern("NormalGeologic-icon.png").similar(0.85f)),
    ConscientiousGeologic(pattern("ConscientiousGeologic-icon.png").similar(0.85f)),
    /** Im Kopfbereich des Sternmenu befindet sich dieser größere Stern */
    StarMenuTitleImage(pattern("StarMenuTitleImage.png").similar(0.90f)),
    StarMenuTitleOrnament(pattern("StarMenuTitleOrnament.png").similar(0.95f)),
    /** Im Fussbereich des Sternmenu befindet sich dieser Tab */
    StarMenuStarTab(pattern("StarMenu-StarTab-Icon.png").similar(0.90f));

    public final Pattern pattern;

    StarMenuButtons(Pattern pattern) {
        this.pattern = pattern;
    }

    public Pattern getPattern() {
        return pattern;
    }
}
