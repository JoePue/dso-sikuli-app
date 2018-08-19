package de.puettner.sikulie.dso;

import lombok.extern.slf4j.Slf4j;
import org.sikuli.script.*;
import org.sikuli.script.Image;

import java.util.Iterator;

import static de.puettner.sikulie.dso.Constants.CHROME_EXE;
import static org.sikuli.script.Commands.click;

@Slf4j
public class SikuliCommands {

    private static SikuliCommands sikuliCommands;
    private final Region appRegion;
    private final Region starMenuRegion;
    private final App app;

    private static final Region DSO_APP_REGION = new Region(10, 115, 1290 - 10, 1047 - 115);

    // !!! Kein static bei Pattern-Properties !!!

    public final Pattern LetsPlayButton = pattern("LetsPlay-Button.png").similar(0.8f);

    private SikuliCommands(App app, Region appRegion, Region starMenuRegion) {
        this.app = app;
        this.appRegion = appRegion;
        this.starMenuRegion = starMenuRegion;
        log.info(app.getWindow());
    }

    public static SikuliCommands build() {

        if (sikuliCommands == null) {
            ImagePath.add("../dso_1.sikuli");
            // setROI(10, 115, 1290 - 10, 1047 - 115)
            sikuliCommands = new SikuliCommands(new App(CHROME_EXE), DSO_APP_REGION, null);
        }
        return sikuliCommands;
    }

    // **********************************************************************************************

    public void sleep(int seconds) {
        try {
            Thread.currentThread().sleep(seconds * 1000);
        } catch (InterruptedException e) {
            log.error(e.getMessage(), e);
        }
    }

    private Match getLastMatch() {
        return appRegion.getLastMatch();
    }

    public static Pattern pattern(String filename) {
        Image img = Image.create(filename);
        return new Pattern(img);
    }

    int type(Object input) {
        return this.type(input.toString(), 0);
    }
    int type(Object input, int modifiers) {
        return appRegion.type(input.toString(), modifiers);
    }


    private int hover(Match match) {
        try {
            return appRegion.hover(match);
        } catch (FindFailed e) {
            throw new FindFailedRTException(e);
        }
    }

    private Match find(Pattern pattern) {
        try {
            return appRegion.find(pattern);
        } catch (FindFailed e) {
            throw new FindFailedRTException(e);
        }
    }

    Iterator<Match> findAll(Pattern pattern) throws FindFailed {
        //        try {
        return appRegion.findAll(pattern);
        //        } catch (FindFailed e) {
        //            throw new FindFailedRTException(e);
        //        }
    }

    private void switchApp() {
        app.focus(1);
    }

    private String str(Object o) {
        return o.toString();
    }

    private void print(Object o) {
        log.info(o.toString());
        System.out.println(o.toString());
    }

    // **********************************************************************************************

    public void switchToBrowser() {
        app.focus(1);
    }

    public void highlightAppRegion() {
        this.appRegion.highlight(1, "green");
    }

    public Location clickDsoTab() {
        return click("DSOTabIcon.png");
    }

    public boolean clickLetsPlayButton() {
        int flag = 0;
        try {
            flag = appRegion.click(LetsPlayButton);
        } catch (FindFailed findFailed) {
            findFailed.printStackTrace();
        }
        //        Match m = find(p);
        //        m.click();
        return (flag == 1 ? true : false);
    }

    public boolean existsLetsPlayButton() {
        return this.exists(LetsPlayButton);
    }

    public boolean waitUntilDsoIsLoaded() {
        return false;
    }

    public Location parkMouse() {
        return click(new Location(100, 1000));
    }

    private <PSI> boolean exists(PSI filename) {
        Match match = appRegion.exists(filename);
        boolean exists = (match == null ? false : true);
        log.debug("exists " + filename + "? " + exists);
        return exists;
    }

    public boolean closeMenu() {
        if (exists("Close-icon.png")) {
            click(appRegion.getLastMatch());
        }
        return true;
    }

    public boolean clickOkButton() {
        if (exists("OkButton-icon.png")) {
            click(getLastMatch());
            sleep(1);
        }
        return true;
    }

    public boolean clickStarMenu() {
        click(pattern("StarMenu-icon.png").targetOffset(-2, -25));
        return true;
    }

    public boolean isStarMenuOpen() {
        if (!exists("StarMenu-Button.png")) {
            return true;
        }
        return true;
    }

    public boolean openStarMenu(String searchString) {
        if (isStarMenuOpen()) {
            clickStarMenu();
            sleep(1);
            if (searchString != null) {
                find(pattern("zoom-icon.png").targetOffset(-43, -3));
                type("");
                type(searchString);
                parkMouse();

            }
        }
        return true;
    }

    public boolean closeStarMenu() {
        if (isStarMenuOpen()) {
            click(getLastMatch());
            return true;
        }
        return false;
    }

//    public boolean openStarMenuForExplorer() {
//        openStarMenu("entd|kund");
//        return true;
//    }

    public boolean focusApp() {
        switchApp();
        return true;
    }

    public boolean closeWelcomeDialog() {
        log.info("closeWelcomeDialog");
        int timeout = 300;
        print("timeout: " + str(timeout));
        while (timeout > 0) {
            sleep(1);
            timeout -= 1;
            if (existsAvatar()) {
                timeout = 0;
                if (exists(pattern("Ok-Button-1.png").exact())) {
                    click(getLastMatch());
                    sleep(1);
                    // Login Bonus
                    if (exists(pattern("Ok-Button-2.png").similar(0.90f))) {
                        click(getLastMatch());
                    }
                }
            }
        }
        return true;
    }

    public boolean existsAvatar() {
        return this.exists("Avatar.png");
    }

    /*
    public boolean startApp() {
        print "Start DSO"
        focusApp()
        click("DSOTabIcon.png")
        click("LetsPlay-Button.png")
        parkMouse()
        closeWelcomeDialog()

        return true;
    }
    */

    public boolean openQuestBook() {
        click("Questbook-icon.png");
        return true;
    }

    public <PSI> boolean launchExplorer(PSI icon) {
        print("launchExplorer "/* + str(icon.x) + " " + str(icon.y)*/);
        click(icon);
        click(pattern("TreasureFind-icon.png").targetOffset(49, -1));
        click(pattern("TreasureSearchVeryLong.png").targetOffset(53, 0));
        clickOkButton();
        parkMouse();
        return true;
    }

    public <PSI> boolean launchAllExplorerByImage(PSI image) {
        print("launchAllExplorerByImage");
        openStarMenu("");
        // try {
        while (exists(image)) {
            //        print "Found { " + str(len(icons))
            launchExplorer(getLastMatch());
            sleep(1);
            openStarMenu("");
        }
        /*} catch (FindFailed f) {
            print("No Explorers found");
        }*/
        return true;
    }

    // ST KU MA EI KO GO GR TI SA
    public <PSI> boolean launchGeologic(PSI icon, String material) {
        print("launchGeologic " /*+ str(icon.x) + " " + str(icon.y)*/);
        click(icon);
        if (material == "ST") {
            click("Material-Stone-Button.png");
        } else if (material == "KU") {
            click("Material-Copper-Button.png");
        } else if (material == "GR") {
            click("Material-Granite-Button.png");
        } else if (material == "MA") {
            click("Material-Marble-Button.png");
        } else if (material == "EI") {
            click("Material-Iron-Button.png");
        }
        clickOkButton();
        return true;
    }

    public <PSI> boolean launchAllGeologicsByImage(PSI image, String material, int launchLimit) {
        print("launchAllGeologicsByImage");
        openStarMenu("");
        int launchCount = 0;
        // try {
        while (exists(image)) {
            launchCount += 1;
            if (launchCount > launchLimit) {
                break;
            }
            launchGeologic(getLastMatch(), material);
            sleep(1);
            openStarMenu("");
            if (launchCount == 0) {
                openStarMenu("");
            }
        }
        // } catch (FindFailed f) {
        //     print("No Geologic found");
        // }
        return true;
    }

    public boolean existsDailyQuestMenuIem() {
        return this.exists(pattern("DailyQuestMenuItem-icon.png").targetOffset(-1, 29));
    }


}
