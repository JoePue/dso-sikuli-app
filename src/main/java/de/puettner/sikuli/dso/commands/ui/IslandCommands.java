package de.puettner.sikuli.dso.commands.ui;

import lombok.extern.slf4j.Slf4j;
import org.sikuli.script.App;
import org.sikuli.script.Match;
import org.sikuli.script.Pattern;
import org.sikuli.script.Region;

@Slf4j
public class IslandCommands extends SikuliCommands {

    protected final String[] okButtonList = {"Ok-Button-0.png", "Ok-Button-1.png", "Ok-Button-2.png", "Ok-Button-3-Bookbinder.png"};
    protected final Pattern LetsPlayButton = pattern("LetsPlay-Button.png").similar(0.8f);
    private final Region geologicSearchRegion;

    public IslandCommands(App app, Region dsoAppRegion, Region geologicSearchRegion) {
        super(app, dsoAppRegion);
        this.geologicSearchRegion = geologicSearchRegion;
    }

    public boolean clickLetsPlayButtonIfExists() {
        return this.clickIfExists(LetsPlayButton, appRegion);
    }

    public boolean existsAvatar() {
        return this.exists(pattern("Avatar.png").similar(0.80f));
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

    public boolean clickBigOkButton() {
        return clickOkButton(1);
    }

    public boolean clickLoginBonusButton() {
        return clickOkButton(2);
    }

    public boolean clickStarButton() {
        log.info("clickStarButton");
        return clickIfExists(pattern("StarButton.png").targetOffset(-2, -25), appRegion);
    }

    public void clickButton(BuildMenuButtons entry) {
        clickIfExists(entry.pattern, appRegion);
    }

    public boolean openQuestBook() {
        clickIfExists(pattern("Questbook-icon.png"), appRegion);
        return true;
    }

    public boolean existsDailyQuestMenuIem() {
        return exists(pattern("DailyQuestMenuItem-icon.png").targetOffset(-1, 29));
    }

    public void clickBookbinderBuilding() {
        this.click(IslandButtons.BookbinderBuilding.pattern);
    }

    public void clickExitButton() {
        click(pattern("exit-button.png"));
    }

    public boolean clickBuildCancelButton() {
        return clickIfExists(pattern("BuildCancelButton.png").similar(0.80f), appRegion);
    }

    public void hightlightRegions() {
        super.hightlightRegions();
        this.geologicSearchRegion.highlight(2, "green");
    }

    public void typeKeyDown(int count) {
        for (int i = 0; i < count; ++i) {
            super.typeKeyDown();
        }
    }
}
