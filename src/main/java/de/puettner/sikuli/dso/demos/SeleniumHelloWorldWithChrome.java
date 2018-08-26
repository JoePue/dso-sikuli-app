package de.puettner.sikuli.dso.demos;

import de.puettner.sikuli.dso.commands.os.WindowsPlatform;

public class SeleniumHelloWorldWithChrome {

    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver", WindowsPlatform.BINS_CHROME_DRIVER_WIN32_EXE);
        //ChromeDriver driver = new ChromeDriver();
    }
}
