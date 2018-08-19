package de.puettner.sikulie.dso;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by joerg.puettner on 19.08.2018.
 */
public class SikuliCommandsTest {

    private final SikuliCommands sikuliCmd = SikuliCommands.build();

    @Test
    public void existsLetsPlayButton() {
        sikuliCmd.existsLetsPlayButton();
    }

}
