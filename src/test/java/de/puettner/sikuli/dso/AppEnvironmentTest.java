package de.puettner.sikuli.dso;

import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

public class AppEnvironmentTest {

    @Test
    public void buildWithEmptyArray() {
        String[] args = {""};
        AppEnvironment env = AppEnvironment.Builder.build(args);
        assertNotNull(env);
        assertTrue(env.getHomeDir().isDirectory());
    }

    @Test
    public void buildWithValidHomeDir() {
        // given
        final String DIR = "D:\\dev-tools\\sikuli\\workspace\\dso-sikuli-app-idea\\dist";
        final String[] args = {AppEnvironment.Builder.DSO_SIKULI_APP_HOME + "=" + DIR};
        // when
        AppEnvironment env = AppEnvironment.Builder.build(args);
        // then
        assertResult(DIR, env);
    }

    private void assertResult(String dir, AppEnvironment env) {
        assertNotNull(env);
        assertNotNull(env.getHomeDir());
        assertEquals(dir, env.getHomeDir().getAbsolutePath());
        assertTrue(env.appendFilename("test.txt").getAbsolutePath().indexOf(File.separator + "." + File.separator) == -1);
    }

    @Test
    public void buildWithEmptyParamValue() {
        // given
        final String DIR = (new File(".")).getAbsolutePath();
        final String[] args = {AppEnvironment.Builder.DSO_SIKULI_APP_HOME + "="};
        // when
        AppEnvironment env = AppEnvironment.Builder.build(args);
        // then
        assertResult(DIR, env);
    }

    @Test
    public void buildWithoutParamValueSeparator() {
        // given
        final String DIR = (new File(".")).getAbsolutePath();
        final String[] args = {AppEnvironment.Builder.DSO_SIKULI_APP_HOME};
        // when
        AppEnvironment env = AppEnvironment.Builder.build(args);
        // then
        assertResult(DIR, env);
    }
}
