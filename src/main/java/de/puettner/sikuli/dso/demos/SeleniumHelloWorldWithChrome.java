package de.puettner.sikuli.dso.demos;

import de.puettner.sikuli.dso.commands.os.WindowsOS;

public class SeleniumHelloWorldWithChrome {

    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver", WindowsOS.BINS_CHROME_DRIVER_WIN32_EXE);
        //ChromeDriver driver = new ChromeDriver();
    }
}
