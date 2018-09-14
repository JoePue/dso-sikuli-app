package de.puettner.sikuli.dso.commands.ui;

import lombok.extern.java.Log;
import org.sikuli.script.App;
import org.sikuli.script.Match;
import org.sikuli.script.Pattern;
import org.sikuli.script.Region;

import java.util.function.BooleanSupplier;
import java.util.logging.Level;

import static de.puettner.sikuli.dso.commands.ui.IslandButtons.*;

@Log
public class IslandCommands extends SikuliCommands {

    protected final String[] okButtonList = {"Ok-Button-0.png", "Ok-Button-1.png", "Ok-Button-2.png", "Ok-Button-3-Bookbinder.png"};
    protected final Pattern LetsPlayButton = pattern("LetsPlay-Button.png").similar(0.8f);

    public IslandCommands(App app, Region appRegion) {
        super(app, appRegion);
    }

    public boolean clickLetsPlayButton() {
        return this.clickIfExists(LetsPlayButton, appRegion);
    }

    public int hover() {
        return appRegion.hover();
    }

    public boolean existsAvatar() {
        return this.exists(Avatar.getPattern());
    }

    public boolean clickSmallOkButton() {
        return this.clickOkButton(OkButton.SMALL_OK);
    }

    protected boolean clickOkButton(MenuButton okButton) {
        boolean rv = false;
        if (okButton == null || okButton.getPattern() == null) {
            throw new IllegalArgumentException();
        }
        Match match = appRegion.exists(okButton.getPattern());
        if (match != null) {
            match.hover();
            match.click();
            sleep();
            rv = true;
            log.info("clickSmallOkButton[" + removePath(okButton.getPattern().getFilename()) + "]" + (rv ? " Clicked" : " not found"));
        } else {
            log.log(Level.SEVERE, "Missing OkButton_" + removePath(okButton.getPattern().getFilename()));
        }
        return rv;
    }

    public boolean clickBigOkButton() {
        return clickOkButton(OkButton.BIG_OK);
    }

    public boolean clickLoginBonusButton() {
        return clickOkButton(OkButton.LOGIN_OK);
    }

    public boolean clickStarButton() {
        log.info("clickStarButton");
        return clickIfExists(StarButton, appRegion);
    }

    public boolean openQuestBook() {
        log.info("openQuestBook");
        return clickIfExists(QuestBookIcon, appRegion);
    }

    public boolean clickDailyQuestMenuItem() {
        log.info("clickDailyQuestMenuItem");
        return clickIfExists(pattern("DailyQuestMenuItem-icon.png").targetOffset(-1, 29), appRegion);
    }

    public boolean clickBookbinderBuilding() {
        log.info("clickBookbinderBuilding");
        return this.click(IslandButtons.BookbinderBuilding.pattern);
    }

    public boolean clickExitButton() {
        log.info("clickExitButton");
        return click(pattern("exit-button.png"));
    }

    public boolean clickBuildCancelButton() {
        log.info("clickBuildCancelButton");
        return clickIfExists(pattern("BuildCancelButton.png").similar(0.80f), appRegion);
    }

    public void typeKeyDown(int count) {
        for (int i = 0; i < count; ++i) {
            super.typeKeyDown();
        }
    }

    public boolean closeChat() {
        return clickIfExists(pattern("Chat-Close-Icon.png").similar(0.95).targetOffset(0, -8), appRegion);
    }

    public Region getIslandRegion() {
        return new Region(super.appRegion);
    }

    protected boolean waitUntilExists(BooleanSupplier supplier) {
        for (int i = 0; i < 10; ++i) {
            if (supplier.getAsBoolean()) {
                return true;
            } else {
                sleep();
            }
        }
        return false;
    }
}
