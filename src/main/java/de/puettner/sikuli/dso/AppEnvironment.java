package de.puettner.sikuli.dso;

import de.puettner.sikuli.dso.exception.InvalidParameterException;
import lombok.Getter;

import java.io.File;

@Getter
public class AppEnvironment {

    private static AppEnvironment instance;
    private final File homeDir;

    private AppEnvironment(File homeDir) {
        this.homeDir = homeDir;
    }

    private AppEnvironment() {
        this.homeDir = new File(".");
    }

    public static AppEnvironment getInstance() {
        return getInstance(new String[0]);
    }

    public static AppEnvironment getInstance(String[] args) {
        if (instance == null) {
            instance = Builder.build(args);
        }
        return instance;
    }

    public File appendFilename(String filename) {
        String path = homeDir.getAbsolutePath();
        if (path.endsWith(".")) {
            path = path.substring(0, path.length() - 2);
        }
        return new File(path + File.separator + filename);
    }

    static class Builder {
        public static final String DSO_SIKULI_APP_HOME = "--configDir".toUpperCase();

        public static AppEnvironment build(String[] args) {
            for (String arg : args) {
                if (arg.isEmpty()) {
                    continue;
                }
                String argToUpper = arg.toUpperCase();
                if (argToUpper.startsWith(DSO_SIKULI_APP_HOME) && argToUpper.length() > DSO_SIKULI_APP_HOME.length()) {
                    String paramValue = arg.substring(DSO_SIKULI_APP_HOME.length() + 1);
                    if (paramValue.isEmpty()) {
                        return new AppEnvironment();
                    }
                    File homeDir = new File(paramValue);
                    if (homeDir.isDirectory()) {
                        return new AppEnvironment(homeDir);
                    } else {
                        throw new InvalidParameterException("Parameter " + DSO_SIKULI_APP_HOME + " is invalid: ");
                    }
                }
            }
            return new AppEnvironment();
        }
    }
}
