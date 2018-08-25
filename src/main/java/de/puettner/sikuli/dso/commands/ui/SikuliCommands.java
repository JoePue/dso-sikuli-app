package de.puettner.sikuli.dso.commands.ui;

import de.puettner.sikuli.dso.exception.FindFailedRTException;
import lombok.extern.slf4j.Slf4j;
import org.sikuli.script.*;

import java.util.Collections;
import java.util.Iterator;

@Slf4j
public class SikuliCommands {

    public final String[] okButtonList = {"Ok-Button-0.png", "Ok-Button-1.png", "Ok-Button-2.png", "Ok-Button-3-Bookbinder.png"};
    public final Pattern LetsPlayButton = pattern("LetsPlay-Button.png").similar(0.8f);
    protected final Region appRegion;

    // !!! Kein static bei Pattern-Properties !!!
    private final App app;

    SikuliCommands(App app, Region appRegion) {
        this.app = app;
        this.appRegion = appRegion;
        log.info(app.getWindow());
    }

    // **********************************************************************************************

    public int paste(Object input) {
        return appRegion.paste(input.toString());
    }

    public int typeESC() {
        return this.type(Key.ESC.toString(), null);
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

    public int type(Object input) {
        return this.type(input.toString(), null);
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

    private String str(Object o) {
        return o.toString();
    }

    private void print(Object o) {
        log.info(o.toString());
    }

    public void switchToBrowser() {
        app.focus(1);
    }

    public Location clickDsoTab() {
        return org.sikuli.script.Commands.click("DSOTabIcon.png");
    }

    // **********************************************************************************************

    public Location clickByLocation(Location location) {
        return org.sikuli.script.Commands.click(location);
    }

    public boolean clickLetsPlayButtonIfExists() {
        return this.clickIfExists(LetsPlayButton, appRegion);
    }

    boolean clickIfExists(Pattern filename, Region searchRegion) {
        final Match match = searchRegion.exists(filename);
        boolean rv = false;
        if (match != null) {
            match.click();
            rv = true;
        }
        log.info(filename + (rv ? " exists and clicked" : " not exists and not clicked"));
        return rv;
    }

    public Location parkMouse() {
        return org.sikuli.script.Commands.click(new Location(100, 1000));
    }

    public boolean closeMenu() {
        return this.clickIfExists(pattern("Close-icon.png"), appRegion);
    }

    public static Pattern pattern(String filename) {
        Image img = Image.create(filename);
        return new Pattern(img);
    }

    public boolean clickSmallOkButton() {
        return this.clickOkButton(0);
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

    public void sleep(int seconds) {
        try {
            Thread.currentThread().sleep(seconds * 1000);
        } catch (InterruptedException e) {
            log.error(e.getMessage(), e);
        }
    }

    public boolean clickBigOkButton() {
        return this.clickOkButton(1);
    }

    public boolean clickLoginBonusButton() {
        return this.clickOkButton(2);
    }

    public boolean clickStarButton() {
        log.info("clickStarButton");
        return clickIfExists(pattern("StarButton.png").targetOffset(-2, -25), appRegion);
    }

    public void focusApp() {
        switchApp();
    }

    private void switchApp() {
        app.focus(1);
    }

    public boolean existsAvatar() {
        return this.exists(pattern("Avatar.png").similar(0.80f));
    }

    boolean exists(Pattern filename) {
        return this.exists(filename, appRegion);
    }

    boolean exists(Pattern filename, Region searchRegion) {
        final Match match = find(filename, searchRegion);
        boolean exists = (match == null ? false : true);
        log.info(filename + " " + (exists ? "exists" : "not exists"));
        return exists;
    }

    public Match find(Pattern filename, Region searchRegion) {
        final Match match = searchRegion.exists(filename);
        return match;
    }

    public boolean openQuestBook() {
        clickIfExists(pattern("Questbook-icon.png"), appRegion);
        return true;
    }

    public boolean existsDailyQuestMenuIem() {
        return this.exists(pattern("DailyQuestMenuItem-icon.png").targetOffset(-1, 29));
    }

    public void clickBookbinderBuilding() {
        this.click(pattern("BookBinderBuilding.png").similar(0.80f));
    }

    boolean click(Pattern filename) {
        return this.clickIfExists(filename, appRegion);
    }

    public void clickExitButton() {
        click(pattern("exit-button.png"));
    }

    public Iterator<Match> findMines(MaterialType material) {
        if (MaterialType.KU.equals(material)) {
            return this.findAll(MaterialType.KU.sourcePattern, appRegion);
        } else if (MaterialType.GO.equals(material)) {
            return this.findAll(MaterialType.GO.sourcePattern, appRegion);
        } else if (MaterialType.EI.equals(material)) {
            return this.findAll(MaterialType.EI.sourcePattern, appRegion);
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
