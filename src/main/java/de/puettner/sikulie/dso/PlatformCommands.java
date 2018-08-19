package de.puettner.sikulie.dso;

import static de.puettner.sikulie.dso.Constants.CHROME_EXE;

public class PlatformCommands {

    private final WindowsPlatformHelper wph = new WindowsPlatformHelper();

    public boolean isChromeRunning() {
        return wph.isProcessRunning(CHROME_EXE);
    }
}
