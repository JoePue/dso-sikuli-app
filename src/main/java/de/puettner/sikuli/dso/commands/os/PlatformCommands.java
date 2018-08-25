package de.puettner.sikuli.dso.commands.os;

import de.puettner.sikuli.dso.WindowsPlatformHelper;

import static de.puettner.sikuli.dso.WindowsPlatformHelper.CHROME_EXE;

public class PlatformCommands {

    private final WindowsPlatformHelper wph = new WindowsPlatformHelper();

    public boolean isChromeRunning() {
        return wph.isProcessRunning(CHROME_EXE);
    }
}
