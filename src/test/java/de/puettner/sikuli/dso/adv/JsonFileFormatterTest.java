package de.puettner.sikuli.dso.adv;

import org.junit.Test;

import java.io.IOException;

public class JsonFileFormatterTest {

    @Test
    public void format() throws IOException {
        JsonFileFormatter.format(BraveTailorAdv.stateFile);
    }
}
