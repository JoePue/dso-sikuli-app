package de.puettner.sikuli.dso;

import de.puettner.sikuli.dso.adv.FileService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class InstanceBuilder {

    private static List<String> args = new ArrayList();
    private static AtomicBoolean initFlag = new AtomicBoolean(false);

    public static AppEnvironment buildAppEnvironment() {
        return AppEnvironment.getInstance(args, buildFileService());
    }

    public static FileService buildFileService() {
        return new FileService();
    }

    public static void init(String[] newArgs) {
        if (initFlag.compareAndSet(false, true)) {
            args = Arrays.asList(newArgs);
        }
    }

    public static List<String> getArgs() {
        return args;
    }
}
