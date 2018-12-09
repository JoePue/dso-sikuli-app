package de.puettner.sikuli.dso.adv;

import lombok.extern.java.Log;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;

@Log
public class FileServiceTest {

    public static final String TEST_CONFIG_JSON = "test-config.json";

    private FileService sut;

    @Before
    public void before() throws URISyntaxException {
        URL url = this.getClass().getClassLoader().getResource(TEST_CONFIG_JSON);
        log.info("url: " + url);
        File file = new File(url.toURI());
        log.info("file: " + file);
        sut = new FileService();
    }

    @Test
    public void saveState() {
        File file = new File("test.txt");
        AdventureState state = sut.restoreAdventureState(file);
        sut.saveAdventureState(state, file);
    }
}
