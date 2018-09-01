package de.puettner.sikuli.dso;

import de.puettner.sikuli.dso.commands.os.WindowsPlatform;
import de.puettner.sikuli.dso.commands.ui.CommandBuilder;

public class DSOServiceBuilder {

    private static final CommandBuilder cmdBuilder = CommandBuilder.build();
    private static final WindowsPlatform winPlatfom = new WindowsPlatform();
    private static DSOService dsoService = null;

    public static DSOService build() {
        if (dsoService == null) {
            dsoService = new DSOService(winPlatfom, cmdBuilder.buildIslandCommand(), cmdBuilder.buildBuildMenuCommands(), cmdBuilder
                    .buildStarMenuCommands(), cmdBuilder.buildBookbinderMenuCommands(), cmdBuilder.buildBuildQueueMenuCommands(),
                    cmdBuilder.buildQuestBookMenuCommands());
        }
        return dsoService;
    }
}
