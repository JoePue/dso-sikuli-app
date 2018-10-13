package de.puettner.sikuli.dso.adv;

import org.junit.Test;

import java.io.IOException;

/**
 * Created by joerg.puettner on 13.10.2018.
 */
public class JsonFileFormatterTest {

    @Test
    public void format() throws IOException {
        JsonFileFormatter.format(BraveTailorAdv.stateFile);
    }
}
