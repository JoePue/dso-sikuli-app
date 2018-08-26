package de.puettner.sikuli.dso.commands.os;

import static de.puettner.sikuli.dso.commands.os.WindowsOS.CHROME_EXE;

public class PlatformCommands {

    private final WindowsOS wph = new WindowsOS();

    public boolean isChromeRunning() {
        return wph.isProcessRunning(CHROME_EXE);
    }
}
