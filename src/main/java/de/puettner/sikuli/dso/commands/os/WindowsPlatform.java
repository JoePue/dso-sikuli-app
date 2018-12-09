package de.puettner.sikuli.dso.commands.os;

import de.puettner.sikuli.dso.AppEnvironment;
import de.puettner.sikuli.dso.InstanceBuilder;
import org.sikuli.script.Region;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.List;

/**
 * Documentation of cmdow: https://ritchielawrence.github.io/cmdow/
 */
public class WindowsPlatform {

    public static final String BINS_CHROME_DRIVER_WIN32_EXE = "bins/chromedriver_win32-v2.41.exe";
    public static final String BINS_CHROME_DRIVER_WIN32_EXE_ABS_PATH =
            "D:\\sikuli\\workspace\\dso_1-sikuli-idea\\bins\\chromedriver_win32-v2.41.exe";
    public static final String CHROME_EXE = "chrome.exe";
    public static final String CMDOW_EXE = "bins/cmdow.exe";
    private static final String CHROME_PROCESS_CAPTION_REGEX = "* - Google Chrome";
    private final AppEnvironment appEnvironment;

    public WindowsPlatform(AppEnvironment appEnvironment) {
        this.appEnvironment = appEnvironment;
    }

    public boolean isChromeBrowserRunning() {
        return this.isProcessRunning(CHROME_EXE);
    }

    public boolean isProcessRunning(String processName) {
        return this.getTaskList().contains(processName);
    }

    public String getTaskList() {
        return toString(startProcess(Collections.singletonList("tasklist.exe")).getInputStream());
    }

    // http://stackoverflow.com/a/5445161/3764804
    private String toString(InputStream inputStream) {
        Scanner scanner = new Scanner(inputStream, "UTF-8").useDelimiter("\\A");
        String string = scanner.hasNext() ? scanner.next() : "";
        scanner.close();
        return string;
    }

    private Process startProcess(List<String> arguments) {
        ProcessBuilder processBuilder = new ProcessBuilder(arguments);
        Process process;
        try {
            process = processBuilder.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return process;
    }

    /**
     * maximize window: "cmdow.exe 0xC5105A  /max"
     */
    public void maximizeBrowserWindow() {
        CmdowOuput output = getDsoBrowserDimension();
        startCmdOwProcess(output.getHandle(), "/max");
    }

    /**
     * Get Window informaton data: cmdow.exe "* - Google Chrome"  /p <br> hide window:     "cmdow.exe 0xC5105A  /hid"
     */
    public CmdowOuput getDsoBrowserDimension() {
        Process process = startCmdOwProcess(CHROME_PROCESS_CAPTION_REGEX, "/p");
        String line;
        Scanner scanner = new Scanner(process.getInputStream());
        line = scanner.nextLine();
        CmdowOuput output = null;
        if (line.startsWith("Handle") && scanner.hasNextLine()) {
            output = CmdowOuput.builder().handle(scanner.next()).build();
            scanner.next();
            output.setPid(scanner.nextInt());
            scanner.next();
            scanner.next();
            scanner.next();
            scanner.next();
            output.setRegion(new Region(scanner.nextInt(), scanner.nextInt(), scanner.nextInt(), scanner.nextInt()));
        }
        scanner.close();
        return output;
    }

    private Process startCmdOwProcess(String... args) {
        List<String> arguments = new ArrayList<>();
        arguments.add(CMDOW_EXE);
        arguments.addAll(Arrays.asList(args));

        return startProcess(arguments);
    }

    /**
     * restore window:  "cmdow.exe 0xC5105A  /res"
     */
    public void restoreBrowserWindow() {
        CmdowOuput output = getDsoBrowserDimension();
        startCmdOwProcess(output.getHandle(), "/res");
    }

    public void standby() {
        startProcess(Arrays.asList("shutdown", "/h"));
    }

    public void runApplication(String applicationFilePath) throws IOException, InterruptedException {
        File application = new File(applicationFilePath);
        String applicationName = application.getName();

        if (!isProcessRunning(applicationName)) {
            Desktop.getDesktop().open(application);
        }
    }

    public static class Builder {
        public static WindowsPlatform build() {
            return new WindowsPlatform(InstanceBuilder.buildAppEnvironment());
        }
    }

}
