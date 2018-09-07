package de.puettner.sikuli.dso.adv;

import de.puettner.sikuli.dso.commands.ui.CommandBuilder;
import de.puettner.sikuli.dso.commands.ui.SikuliCommands;
import org.junit.Before;
import org.junit.Test;

public class TapferesSchneiderleinATTest {


    private final SikuliCommands sikuliCmd = CommandBuilder.build().buildIslandCommand();
    private final TapferesSchneiderleinAT adventure = AdventureBuilder.build().buildTapferesSchneiderleinAT();

    @Before
    public void before() {
        sikuliCmd.focusBrowser();
    }

    @Test
    public void play() {
        adventure.play();
    }
}
