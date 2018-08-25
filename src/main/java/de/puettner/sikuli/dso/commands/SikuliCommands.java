package de.puettner.sikuli.dso.commands;

import de.puettner.sikuli.dso.FindFailedRTException;
import lombok.extern.slf4j.Slf4j;
import org.sikuli.script.*;

import java.util.Collections;
import java.util.Iterator;

import static de.puettner.sikuli.dso.Constants.CHROME_EXE;

@Slf4j
public class SikuliCommands {

    private static SikuliCommands sikuliCommands;
    protected final Region appRegion;
    private final Region starMenuRegion;
    private final Region buildListRegion;
    private final App app;

    public final String[] okButtonList = {"Ok-Button-0.png", "Ok-Button-1.png", "Ok-Button-2.png", "Ok-Button-3-Bookbinder.png"};

    private static final Region DSO_APP_REGION = new Region(10, 115, 1290 - 10, 1047 - 115);

    // !!! Kein static bei Pattern-Properties !!!

    public final Pattern LetsPlayButton = pattern("LetsPlay-Button.png").similar(0.8f);

    protected SikuliCommands(App app, Region appRegion, Region starMenuRegion, Region buildListRegion) {
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

    public BuildMenuCommands buildBuildMenuCommands() {
        return new BuildMenuCommands(appRegion, this);
    }

    public StarMenuCommands buildStarMenuCommands() {
        return new StarMenuCommands(starMenuRegion, this);
    }

    public BookbinderMenuCommands buildBookbinderMenuCommands() {
        return new BookbinderMenuCommands(appRegion, this);
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

    public static Pattern pattern(String filename) {
        Image img = Image.create(filename);
        return new Pattern(img);
    }

    public int paste(Object input) {
        return appRegion.paste(input.toString());
    }

    public int typeESC() {
        return this.type(Key.ESC.toString(), null);
    }

    public int type(Object input) {
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

    public int hover(Match match) {
        try {
            return appRegion.hover(match);
        } catch (FindFailed e) {
            throw new FindFailedRTException(e);
        }
    }

    public Iterator<Match> findAll(Pattern pattern) {
        return this.findAll(pattern, appRegion);
    }

    Iterator<Match> findAll(Pattern pattern, Region searchRegion) {
        if (pattern == null || searchRegion == null) {
            throw new IllegalArgumentException(pattern + " " + searchRegion);
        }
        try {
            return searchRegion.findAll(pattern);
        } catch (FindFailed e) {
            log.error(e.getMessage(), e);
        }
        return Collections.emptyListIterator();
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

    public Location parkMouse() {
        return org.sikuli.script.Commands.click(new Location(100, 1000));
    }

    public <PSI> Match find(PSI filename, Region searchRegion) {
        final Match match = searchRegion.exists(filename);
        return match;
    }

    <PSI> boolean exists(PSI filename, Region searchRegion) {
        final Match match = find(filename, searchRegion);
        boolean exists = (match == null ? false : true);
        log.info(filename + " " + (exists ? "exists" : "not exists"));
        return exists;
    }

    <PSI> boolean exists(PSI filename) {
        return this.exists(filename, appRegion);
    }

    <PSI> boolean click(PSI filename) {
        return this.clickIfExists(filename, appRegion);
    }

    <PSI> boolean clickIfExists(PSI filename, Region searchRegion) {
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
        return clickIfExists(pattern("StarMenu-icon.png").targetOffset(-2, -25), appRegion);
    }

    public void focusApp() {
        switchApp();
    }

    public boolean existsAvatar() {
        return this.exists("Avatar.png");
    }

    public boolean openQuestBook() {
        clickIfExists("Questbook-icon.png", appRegion);
        return true;
    }

    public boolean existsDailyQuestMenuIem() {
        return this.exists(pattern("DailyQuestMenuItem-icon.png").targetOffset(-1, 29));
    }


    public void clickBookbinderBuilding() {
        this.click(pattern("BookBinderBuilding.png").similar(0.80f));
    }

    public void clickExitButton() {
        click("exit-button.png");
    }

    public Iterator<Match> findMines(MaterialType material) {
        if (MaterialType.KU.equals(material)) {
            return this.sikuliCommands.findAll(MaterialType.KU.sourcePattern, appRegion);
        } else if (MaterialType.GO.equals(material)) {
            return this.sikuliCommands.findAll(MaterialType.GO.sourcePattern, appRegion);
        } else if (MaterialType.EI.equals(material)) {
            return this.sikuliCommands.findAll(MaterialType.EI.sourcePattern, appRegion);
            //        } else if (MaterialType.MA.equals(material)) {
            //        } else if (MaterialType.KU.equals(material)) {
        } else {
            throw new IllegalArgumentException("Unsupported type: " + material);
        }
    }

    public boolean clickBuildCancelButton() {
        return this.clickIfExists(pattern("BuildCancelButton.png").similar(0.80f), appRegion);
    }
}
