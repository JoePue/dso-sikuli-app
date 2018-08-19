package de.puettner.sikulie.dso;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class SeleniumHelloWorldWithChrome {

    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver", Constants.LIBS_CHROME_DRIVER_WIN32_EXE);

        //ChromeDriver driver = new ChromeDriver();
    }
}
