package de.puettner.sikulie.dso;

import org.openqa.selenium.chrome.ChromeDriver;

public class SeleniumChrome {

    public static void main(String[] args) {
        ChromeDriver driver = new ChromeDriver();
        driver.get("https://www.diesiedleronline.de/de/startseite");
    }
}
