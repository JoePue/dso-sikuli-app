package de.puettner.sikuli.dso;

import de.puettner.sikuli.dso.commands.os.WindowsPlatform;

public abstract class BaseServiceTest {
    protected WindowsPlatform windowsPlatform = WindowsPlatform.Builder.build();
}
