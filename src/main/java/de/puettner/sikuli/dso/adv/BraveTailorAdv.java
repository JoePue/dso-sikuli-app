package de.puettner.sikuli.dso.adv;

import de.puettner.sikuli.dso.commands.ui.IslandCommands;

/**
 * brave tailor = tapferes Schneiderlein
 */
public class BraveTailorAdv extends Adventure {

    protected BraveTailorAdv(IslandCommands islandCmds) {
        super(islandCmds);
    }

    public void play() {
        zoomOut();
    }

    public void zoomOut() {
        islandCmds.type("-");
        islandCmds.sleepX(1);
        islandCmds.type("-");
        islandCmds.sleepX(1);
        islandCmds.type("-");
        islandCmds.sleepX(1);
    }

    public void gotoPosOne() {
        islandCmds.type("0");
        zoomOut();
    }
}
