package de.puettner.sikuli.dso.demos;

import de.puettner.sikuli.dso.Constants;

public class SeleniumHelloWorldWithChrome {

    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver", Constants.BINS_CHROME_DRIVER_WIN32_EXE);
        //ChromeDriver driver = new ChromeDriver();
    }
}
