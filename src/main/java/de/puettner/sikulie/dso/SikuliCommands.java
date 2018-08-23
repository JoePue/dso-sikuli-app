package de.puettner.sikulie.dso;

import lombok.extern.slf4j.Slf4j;
import org.sikuli.script.*;

import java.util.Iterator;

import static de.puettner.sikulie.dso.Constants.CHROME_EXE;

@Slf4j
public class SikuliCommands {

    private static SikuliCommands sikuliCommands;
    private final Region appRegion;
    private final Region starMenuRegion;
    private final Region buildListRegion;
    private final App app;
    public final String[] okButtonList = {"Ok-Button-0.png", "Ok-Button-1.png", "Ok-Button-2.png", "Ok-Button-3-Bookbinder.png"};

    private static final Region DSO_APP_REGION = new Region(10, 115, 1290 - 10, 1047 - 115);

    // !!! Kein static bei Pattern-Properties !!!

    public final Pattern LetsPlayButton = pattern("LetsPlay-Button.png").similar(0.8f);

    private SikuliCommands(App app, Region appRegion, Region starMenuRegion, Region buildListRegion) {
        this.app = app;
        this.appRegion = appRegion;
        this.starMenuRegion = starMenuRegion;
        this.buildListRegion = buildListRegion;
        log.info(app.getWindow());
    }

    public static SikuliCommands build() {
        if (sikuliCommands == null) {
            ImagePath.add("../dso_1.sikuli");
            // setROI(10, 115, 1290 - 10, 1047 - 115)
            Region starMenuRegion = calculateStarMenuRegion();
            Region buildListRegion = calculateBuildListRegion();
            sikuliCommands = new SikuliCommands(new App(CHROME_EXE), DSO_APP_REGION, starMenuRegion, buildListRegion);
        }
        return sikuliCommands;
    }

    private static Region calculateStarMenuRegion() {
        Region region = new Region(0, 0, 0, 0);
        region.w = 650;
        region.h = 600;
        region.x = (int) (0.3 * DSO_APP_REGION.w);
        region.y = (int) (0.45 * DSO_APP_REGION.h);
        return region;
    }

    private static Region calculateBuildListRegion() {
        Region region = new Region(0, 0, 0, 0);
        region.w = 50;
        region.h = 100;
        region.x = (int) (DSO_APP_REGION.w - region.w);
        region.y = DSO_APP_REGION.y;
        return region;
    }

    // **********************************************************************************************

    public void sleep(int seconds) {
        try {
            Thread.currentThread().sleep(seconds * 1000);
        } catch (InterruptedException e) {
            log.error(e.getMessage(), e);
        }
    }

//    @Deprecated
//    private Match getLastMatch() {
//        // return appRegion.getLastMatch();
//        throw new IllegalStateException("Verwendung nicht gestattet.");
//    }

    public static Pattern pattern(String filename) {
        Image img = Image.create(filename);
        return new Pattern(img);
    }

    int paste(Object input) {
        return appRegion.paste(input.toString());
    }

    public int typeESC() {
        return this.type(Key.ESC.toString(), null);
    }

    int type(Object input) {
        return this.type(input.toString(), null);
    }

    /**
     * Using type() with Special Characters or non QWERTY US keyboards https://answers.launchpad.net/sikuli/+faq/933
     */
    public int type(String text, String modifiers) {
        String str = text.toString().replace(':', ' ').replace('|', ' ');
        if (modifiers == null) {
            return appRegion.type(text.toString());
        }
        return appRegion.type(text.toString(), modifiers);
    }

    private int hover(Match match) {
        try {
            return appRegion.hover(match);
        } catch (FindFailed e) {
            throw new FindFailedRTException(e);
        }
    }

    Iterator<Match> findAll(Pattern pattern) throws FindFailed {
        return appRegion.findAll(pattern);
    }

    private void switchApp() {
        app.focus(1);
    }

    private String str(Object o) {
        return o.toString();
    }

    private void print(Object o) {
        log.info(o.toString());
    }

    // **********************************************************************************************

    public void switchToBrowser() {
        app.focus(1);
    }

    public void highlightAppRegions() {
        int secs = 2;
        this.starMenuRegion.highlight(secs, "white");
        this.buildListRegion.highlight(secs, "green");
        this.appRegion.highlight(secs, "green");
    }

    public Location clickDsoTab() {
        return org.sikuli.script.Commands.click("DSOTabIcon.png");
    }

    public Location clickByLocation(Location location) {
        return org.sikuli.script.Commands.click(location);
    }

    public boolean clickLetsPlayButtonIfExists() {
        return this.clickIfExists(LetsPlayButton, appRegion);
    }

    public boolean waitUntilDsoIsLoaded() {
        return false;
    }

    public Location parkMouse() {
        return org.sikuli.script.Commands.click(new Location(100, 1000));
    }

    private <PSI> Match find(PSI filename, Region searchRegion) {
        final Match match = searchRegion.exists(filename);
        return match;
    }

    private <PSI> boolean exists(PSI filename, Region searchRegion) {
        final Match match = find(filename, searchRegion);
        boolean exists = (match == null ? false : true);
        log.info(filename + " " + (exists ? "exists" : "not exists"));
        return exists;
    }

    public <PSI> boolean exists(PSI filename) {
        return this.exists(filename, appRegion);
    }

    private <PSI> boolean click(PSI filename) {
        return this.clickIfExists(filename, appRegion);
    }

    private <PSI> boolean clickIfExists(PSI filename, Region searchRegion) {
        final Match match = searchRegion.exists(filename);
        boolean rv = false;
        if (match != null) {
            match.click();
        }
        log.info(filename + (rv ? " exists and clicked" : " not exists and not clicked"));
        return rv;
    }

    public boolean closeMenu() {
        return this.clickIfExists("Close-icon.png", appRegion);
    }

    public boolean clickSmallOkButton() {
        return this.clickOkButton(0);
    }

    public boolean clickBigOkButton() {
        return this.clickOkButton(1);
    }

    public boolean clickOkButtonBookbinder() {
        return this.clickOkButton(3);
    }

    public boolean clickLoginBonusButton() {
        return this.clickOkButton(2);
    }

    protected boolean clickOkButton(Integer buttonId) {
        boolean rv = false;
        String buttonFilename = null;
        if (buttonId >= 0 && buttonId < okButtonList.length) {
            buttonFilename = okButtonList[buttonId];
        } else {
            throw new IllegalArgumentException();
        }
        Match match = appRegion.exists(buttonFilename);
        if (match != null) {
            match.click();
            sleep(1);
            rv = true;
        }
        log.info("clickSmallOkButton[" + buttonFilename + "]" + (rv ? " Clicked" : " not found"));
        return rv;
    }

    public boolean clickStarMenuButton() {
        log.info("clickStarMenuButton");
        clickIfExists(pattern("StarMenu-icon.png").targetOffset(-2, -25), appRegion);
        return true;
    }

    public boolean isStarMenuOpen() {
        if (exists("StarMenu-Button.png", appRegion)) {
            log.info("StarMenu is open");
            return true;
        }
        log.info("StarMenu is NOT open");
        return false;
    }

    public boolean openStarMenu() {
        return this.openStarMenu(null);
    }

    public boolean openStarMenu(@Deprecated String searchString) {
        log.info("openStarMenu() searchString: " + searchString);
        if (!isStarMenuOpen()) {
            clickStarMenuButton();
            sleep(1);
        }
        if (searchString != null) {
            clickIfExists(pattern("zoom-icon.png").targetOffset(-43, -3), appRegion);
            sleep(1);
            type("a", Key.CTRL);
            sleep(1);
            paste(searchString);
        }
//        this.closeStarMenu();
        parkMouse();
        return true;
    }

    public boolean closeStarMenu() {
        boolean rv = false;
        if (isStarMenuOpen()) {
            log.info("closeStarMenu");
            this.typeESC();
            rv = true;
        } else {
            log.info("closeStarMenu : Not open");
        }
        return rv;
    }

    public boolean focusApp() {
        switchApp();
        return true;
    }

    //    public boolean closeWelcomeDialog() {
    //        log.info("closeWelcomeDialog");
    //        int timeout = 300;
    //        print("timeout: " + str(timeout));
    //        while (timeout > 0) {
    //            sleep(1);
    //            timeout -= 1;
    //            if (existsAvatar()) {
    //                timeout = 0;
    //                if (clickIfExists(pattern("Ok-Button-1.png").exact(), appRegion)) {
    //                    sleep(1);
    //                    // Login Bonus
    //                    if (clickIfExists(pattern("Ok-Button-2.png").similar(0.90f), appRegion)) {
    //
    //                    }
    //                }
    //            }
    //        }
    //        return true;
    //    }

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
        clickIfExists("Questbook-icon.png", appRegion);
        return true;
    }

    public <PSI> boolean launchExplorer(Match match, Region searchRegion) {
        print("launchExplorer()");
        match.click();
        click(pattern("TreasureFind-icon.png").targetOffset(49, -1));
        click(pattern("TreasureSearchVeryLong.png").targetOffset(53, 0));
        clickSmallOkButton();
        return true;
    }

    public int launchAllExplorerByImage(Pattern image) {
        log.info("launchAllExplorerByImage");
        int launchCount = 0;
        do {
            openStarMenu();
            Match match = this.find(image, starMenuRegion);
            if (match != null) {
                launchExplorer(match, starMenuRegion);
                parkMouse();
                launchCount++;
                sleep(1);
            } else {
                log.info("No explorer found. launchCount: " + launchCount);
                break;
            }
        } while (true);
        return launchCount;
    }

    // ST KU MA EI KO GO GR TI SA
    public boolean launchGeologic(Match match, String material) {
        log.info("launchGeologic material: " + material);
        if (match.click() == 1) {
            if (material == "ST") {
                clickIfExists("Material-Stone-Button.png", appRegion);
            } else if (material == "KU") {
                clickIfExists("Material-Copper-Button.png", appRegion);
            } else if (material == "GR") {
                clickIfExists("Material-Granite-Button.png", appRegion);
            } else if (material == "MA") {
                clickIfExists("Material-Marble-Button.png", appRegion);
            } else if (material == "EI") {
                clickIfExists("Material-Iron-Button.png", appRegion);
            }
            return clickSmallOkButton();
        }
        return false;
    }

    public <PSI> int launchAllGeologicsByImage(PSI image, String material, int launchLimit) {
        print("launchAllGeologicsByImage");
        int launchCount = 0;
        for (int i = 0; i <= launchLimit; ++i) {
            openStarMenu();
            Match match = this.find(image, appRegion);
            if (match != null) {
                if (launchGeologic(match, material)) {
                    launchCount++;
                    this.parkMouse();
                    sleep(1);
                }
            } else {
                log.info("No Geologic launched");
                break;
            }
        }
        return launchCount;
    }

    public boolean existsDailyQuestMenuIem() {
        return this.exists(pattern("DailyQuestMenuItem-icon.png").targetOffset(-1, 29));
    }


    public void clickBookbinderBuilding() {
        this.click(pattern("BookBinderBuilding.png").similar(0.80f));
    }

    /**
     * Types: Manuskript, Kompendium, Kodex
     *
     * @param bookType
     */
    public void clickNextBook(String bookType) {
        if ("MA".equals(bookType)) {
            click(pattern("Manusskript-Button.png").similar(0.90f));
        } else if ("KO".equals(bookType)) {
            click(pattern("Kompendium-Button.png").similar(0.90f));
        } else if ("KX".equals(bookType)) {
            click(pattern("Kodex-Button.png").similar(0.90f));
        } else {
            log.error("Unknown bootType: " + bookType);
        }
    }


    public void clickExitButton() {
        click("exit-button.png");
    }
}
