package de.puettner.sikuli.dso;

import de.puettner.sikuli.dso.commands.os.WindowsPlatform;
import de.puettner.sikuli.dso.commands.ui.MenuBuilder;

public class DSOServiceBuilder {

    private static final MenuBuilder menuBuilder = MenuBuilder.build();
    private static final WindowsPlatform winPlatfom = new WindowsPlatform();
    private static DSOService dsoService = null;

    public static DSOService build() {
        if (dsoService == null) {
            dsoService = new DSOService(winPlatfom, menuBuilder.buildIslandCommand(), menuBuilder.buildBuildMenuCommands(), menuBuilder
                    .buildStarMenuCommands(), menuBuilder.buildBookbinderMenuCommands(), menuBuilder.buildBuildQueueMenuCommands(),
                    menuBuilder.buildQuestBookMenuCommands());
        }
        return dsoService;
    }
}
