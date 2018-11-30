package de.puettner;

import lombok.extern.java.Log;

@Log
public class Test {

    @org.junit.Test
    public void test() {
        log.severe("severe log stmt");
        log.warning("warning log stmt");
        log.info("info log stmt");
        log.config("config log stmt");
        log.fine("fine log stmt");
        log.finer("finer log stmt");
        log.finest("finest log stmt");
    }
}
