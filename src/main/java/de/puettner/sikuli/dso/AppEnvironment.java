package de.puettner.sikuli.dso;

import de.puettner.sikuli.dso.adv.FileService;
import de.puettner.sikuli.dso.exception.InvalidParameterException;
import lombok.Getter;

import java.io.File;
import java.util.List;

@Getter
public class AppEnvironment {

    private static AppEnvironment instance;
    private final File homeDir;
    private AppProperties properties;

    private AppEnvironment(File homeDir) {
        this.homeDir = homeDir;
    }

    private AppEnvironment() {
        this.homeDir = new File(".");
    }

    public static AppEnvironment getInstance(List<String> args, FileService fileService) {
        if (instance == null) {
            instance = Builder.build(args, fileService);
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

    private void setProperties(AppProperties properties) {
        this.properties = properties;
    }

    public static class Builder {
        public static final String APP_PROPERTIES_FILENAME = "dso-sikuli-app.properties";
        public static final String DSO_SIKULI_APP_HOME = "--configDir".toUpperCase();

        public static AppEnvironment build(List<String> args, FileService fileService) {
            File homeDir = null;
            for (int i = 0; i < args.size(); ++i) {
                String arg = args.get(i);
                if (arg.isEmpty()) {
                    continue;
                }
                String argToUpper = arg.toUpperCase();
                if (argToUpper.startsWith(DSO_SIKULI_APP_HOME) && argToUpper.length() > DSO_SIKULI_APP_HOME.length()) {
                    String paramValue = arg.substring(DSO_SIKULI_APP_HOME.length() + 1);
                    if (paramValue.isEmpty()) {
                        break;
                    }
                    homeDir = new File(paramValue);
                    args.set(i, null);
                    if (homeDir.isDirectory()) {
                        break;
                    } else {
                        throw new InvalidParameterException("Parameter " + DSO_SIKULI_APP_HOME + " is invalid: ");
                    }
                }
            }
            AppEnvironment env = null;
            if (homeDir == null) {
                env = new AppEnvironment();
            } else {
                env = new AppEnvironment(homeDir);
            }
            env.setProperties(loadAppConfig(env, fileService));
            return env;
        }

        private static AppProperties loadAppConfig(AppEnvironment homeDir, FileService fileService) {
            File appConfigFile = homeDir.appendFilename(APP_PROPERTIES_FILENAME);
            return fileService.loadFile(appConfigFile);
        }
    }
}
