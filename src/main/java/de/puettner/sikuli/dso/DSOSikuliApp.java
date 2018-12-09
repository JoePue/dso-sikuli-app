package de.puettner.sikuli.dso;

import lombok.extern.java.Log;

@Log
public class DsoSikuliApp {

    public static void main(String[] args) {
        log.info("App starting");
        InstanceBuilder.init(args);
        AppEnvironment appEnvironment = InstanceBuilder.buildAppEnvironment();
        log.info("homeDir: " + appEnvironment.getHomeDir());
        AppArgumentProcessor argumentProcessor = new AppArgumentProcessor();
        argumentProcessor.process(args);
    }
}
