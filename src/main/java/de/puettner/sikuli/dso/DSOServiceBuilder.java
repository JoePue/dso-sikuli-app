package de.puettner.sikuli.dso;

import de.puettner.sikuli.dso.commands.os.WindowsPlatform;
import de.puettner.sikuli.dso.commands.ui.CommandBuilder;

public class DSOServiceBuilder {

    private static final CommandBuilder cmdBuilder = CommandBuilder.build();
    private static final WindowsPlatform winCommand = new WindowsPlatform();
    private static DSOServices dsoServices = null;

    public static DSOServices build() {
        if (dsoServices == null) {
            dsoServices = new DSOServices(winCommand, cmdBuilder.buildIslandCommand(), cmdBuilder.buildBuildMenuCommands(), cmdBuilder
                    .buildStarMenuCommands(), cmdBuilder.buildBookbinderMenuCommands(), cmdBuilder.buildBuildQueueMenuCommands(),
                    cmdBuilder.buildQuestBookMenuCommands());
        }
        return dsoServices;
    }
}
