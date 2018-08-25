package de.puettner.sikuli.dso.demos;

import org.openqa.selenium.chrome.ChromeDriver;

public class SeleniumChrome {

    public static void main(String[] args) {
        ChromeDriver driver = new ChromeDriver();
        driver.get("https://www.diesiedleronline.de/de/startseite");
    }
}
