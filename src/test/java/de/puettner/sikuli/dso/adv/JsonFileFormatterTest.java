package de.puettner.sikuli.dso.adv;

import de.puettner.sikuli.dso.InstanceBuilder;
import org.junit.Test;

import java.io.IOException;

public class JsonFileFormatterTest {

    @Test
    public void format() throws IOException {
        JsonFileFormatter.format(InstanceBuilder.buildAppEnvironment().appendFilename("brave-tailor-adventure.json"));
    }
}
