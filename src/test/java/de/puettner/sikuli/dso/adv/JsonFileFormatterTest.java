package de.puettner.sikuli.dso.adv;

import de.puettner.sikuli.dso.AppEnvironment;
import org.junit.Test;

import java.io.IOException;

public class JsonFileFormatterTest {

    @Test
    public void format() throws IOException {
        JsonFileFormatter.format(AppEnvironment.getInstance().appendFilename("brave-tailor-adventure.json"));
    }
}
