package de.puettner.sikuli.dso.commands.ui;

import org.sikuli.script.Match;
import org.sikuli.script.Region;

import java.util.Iterator;

import static de.puettner.sikuli.dso.commands.ui.SikuliCommands.pattern;

public class BuildQueueDsoMenu extends DsoMenu {

    public BuildQueueDsoMenu(Region menuRegion, IslandCommands islandCmds) {
        super(menuRegion, islandCmds);
    }

    public int getBuildQueueSize() {
        Iterator<Match> it = islandCmds.findAll(pattern("BuildQueueEntry.png").similar(0.90f), menuRegion);
        int counter = 0;
        while (it.hasNext()) {
            counter++;
        }
        return counter;
    }
}
