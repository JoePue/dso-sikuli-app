package de.puettner.sikulie.dso;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

public class WindowsPlatformHelper {

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
