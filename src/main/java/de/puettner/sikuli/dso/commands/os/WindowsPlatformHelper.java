package de.puettner.sikuli.dso.commands.os;

import org.sikuli.script.Region;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

public class WindowsPlatformHelper {

    public static final String BINS_CHROME_DRIVER_WIN32_EXE = "bins/chromedriver_win32-v2.41.exe";
    public static final String BINS_CHROME_DRIVER_WIN32_EXE_ABS_PATH =
            "D:\\sikuli\\workspace\\dso_1-sikuli-idea\\bins\\chromedriver_win32-v2.41.exe";
    public static final String CHROME_EXE = "chrome.exe";
    public static final String CMDOW_EXE = "bins/cmdow.exe";
    private static final String CHROME_PROCESS_CAPTION_REGEX = "* - Google Chrome";

    public static CmdowOuput getChromeDimension() {
        return getAppDimensionByCmdow(CMDOW_EXE, "\"" + CHROME_PROCESS_CAPTION_REGEX + "\"");
    }

    static CmdowOuput getAppDimensionByCmdow(String exe, String caption) {
        ProcessBuilder processBuilder = new ProcessBuilder(exe, caption, "/p");
        Process process;
        try {
            process = processBuilder.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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

    public void runApplication(String applicationFilePath) throws IOException, InterruptedException {
        File application = new File(applicationFilePath);
        String applicationName = application.getName();

        if (!isProcessRunning(applicationName)) {
            Desktop.getDesktop().open(application);
        }
    }

    // http://stackoverflow.com/a/19005828/3764804
    public boolean isProcessRunning(String processName) {
        return this.getTaskList().contains(processName);
    }

    public String getTaskList() {
        ProcessBuilder processBuilder = new ProcessBuilder("tasklist.exe");
        Process process;
        try {
            process = processBuilder.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return toString(process.getInputStream());
    }

    // http://stackoverflow.com/a/5445161/3764804
    private String toString(InputStream inputStream) {
        Scanner scanner = new Scanner(inputStream, "UTF-8").useDelimiter("\\A");
        String string = scanner.hasNext() ? scanner.next() : "";
        scanner.close();
        return string;
    }
}
