package de.puettner.sikuli.dso.commands.ui;

import de.puettner.sikuli.dso.AppMath;
import de.puettner.sikuli.dso.LocationMath;
import de.puettner.sikuli.dso.exception.AppException;
import lombok.extern.java.Log;
import org.sikuli.script.*;
import org.sikuli.script.Image;

import java.awt.*;
import java.io.File;
import java.util.Collections;
import java.util.Iterator;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import static de.puettner.sikuli.dso.adv.FileService.removeFilePath;
import static java.lang.String.format;

@Log
public class SikuliCommands {

    public static final int DEFAULT_WAITING = 500;
    public static final int dragDropBoundary = 200;
    // !!! Kein static bei Pattern-Properties !!!
    private final App app;
    protected Region appRegion;

    protected SikuliCommands(App app, Region appRegion) {
        this.app = app;
        this.appRegion = appRegion;
        initRegion(appRegion);
        log.info(app.getWindow());
        Key.addHotkey("j", Key.C_CTRL + Key.C_ALT, new SikuliHotKeyListener());
    }

    public static void initRegion(Region region) {
        region.setAutoWaitTimeout(0);
        region.setFindFailedResponse(FindFailedResponse.SKIP);
        region.setThrowException(false);
    }

    public int paste(Object input) {
        return appRegion.paste(input.toString());
    }

    public int typeESC() {
        sleep(100);
        int rv = this.type(Key.ESC.toString(), null);
        sleep(100);
        return rv;
    }

    /**
     * @param ms milliseconds
     */
    private void sleep(int ms) {
        try {
            if (ms > DEFAULT_WAITING) {
                log.info("sleep() for " + ms + " ms");
            }
            Thread.currentThread().sleep(ms);
        } catch (InterruptedException e) {
            log.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    /**
     * Using stepType() with Special Characters or non QWERTY US keyboards https://answers.launchpad.net/sikuli/+faq/933
     */
    public int type(String text, String modifiers) {
        String str = text.toString().replace(':', ' ').replace('|', ' ');
        if (modifiers == null) {
            return appRegion.type(text.toString());
        }
        return appRegion.type(text.toString(), modifiers);
    }

    public void sleepX(int x) {
        sleep(DEFAULT_WAITING * x);
    }

    public int typeKeyDown() {
        return this.type(Key.DOWN.toString(), null);
    }

    public int type(Object input) {
        return this.type(input.toString(), null);
    }

    public Iterator<Match> findAll(Pattern pattern) {
        Iterator<Match> result = this.findAll(pattern, appRegion);
        if (result == null) {
            result = Collections.emptyListIterator();
        }
        return result;
    }

    /**
     * Findet alle Treffer zum Startzeitpunkt der Suche!
     */
    Iterator<Match> findAll(Pattern pattern, Region searchRegion) {
        log.info("findAll");
        if (pattern == null || searchRegion == null) {
            throw new IllegalArgumentException(pattern + " " + searchRegion);
        }
        try {
            return searchRegion.findAll(pattern);
        } catch (FindFailed e) {
            throw new RuntimeException(e);
        }
    }

    private String str(Object o) {
        return o.toString();
    }

    private void print(Object o) {
        log.info(o.toString());
    }

    public void focusBrowser() {
        app.focus();
        sleep();
    }

    public void sleep() {
        this.sleep(DEFAULT_WAITING);
    }

    public void sleep(int value, TimeUnit tu) {
        if (!TimeUnit.SECONDS.equals(tu)) {
            throw new IllegalArgumentException("Unsupported time unit");
        }
        this.sleep(value * 1000);
    }

    public Location clickDsoTab() {
        Location rv = Commands.click("DSOTabIcon.png");
        sleep();
        return rv;
    }

    public Location parkMouse() {
        return org.sikuli.script.Commands.hover(new Location(appRegion.w / 2, appRegion.h + 50));
    }

    public void parkMouseForMove() {
        hover(new Location(appRegion.w / 2, appRegion.h / 2));
    }

    public void hover(Location location) {
        try {
            appRegion.hover(location);
        } catch (FindFailed findFailed) {
            throw new RuntimeException(findFailed);
        }
    }

    public boolean closeMenu() {
        return this.clickIfExists(pattern("Close-icon.png"), appRegion);
    }

    public boolean clickIfExists(Pattern pattern, Region searchRegion) {
        return clickIfExists(pattern, searchRegion, Optional.empty());
    }

    public static Pattern pattern(String filename) {
        Image img = Image.create(filename);
        return new Pattern(img);
    }

    public boolean clickIfExists(Pattern pattern, Region searchRegion, Optional<Dimension> hoverLocation) {
        final Match match = searchRegion.exists(pattern);
        boolean rv = false;
        if (match != null) {
            match.click();
            if (hoverLocation.isPresent()) {
                try {
                    match.hover(AppMath.add(match, hoverLocation.get()));
                } catch (FindFailed findFailed) {
                    throw new AppException(findFailed);
                }
            }
            sleep(250);
            rv = true;
        }
        log.info(removePath(pattern.getFilename()) + (rv ? " exists and clicked" : " not exists and not clicked"));
        return rv;
    }

    protected String removePath(String filename) {
        if (filename == null || filename.isEmpty()) {
            return "";
        }
        return filename.substring(filename.lastIndexOf(File.separatorChar));
    }

    public boolean clickIfExists(MenuButton menuButton, Region searchRegion) {
        return this.clickIfExists(menuButton.getPattern(), searchRegion);
    }

    boolean click(Pattern pattern) {
        return clickAndHover(pattern, Optional.empty());
    }

    boolean clickAndHover(Pattern pattern, Optional<Dimension> hover) {
        if (clickIfExists(pattern, appRegion, hover)) {
            return true;
        }
        log.log(Level.SEVERE, "Missing pattern");
        return false;
    }


    boolean exists(Pattern filename) {
        return this.exists(filename, appRegion);
    }

    boolean exists(Pattern pattern, Region searchRegion) {
        final Match match = find(pattern, searchRegion);
        boolean exists = (match == null ? false : true);
        log.fine(removeFilePath(pattern.getFilename()) + " " + (exists ? "exists" : "not exists"));
        return exists;
    }

    public Match find(Pattern filename, Region searchRegion) {
        final Match match = searchRegion.exists(filename);
        return match;
    }

    public void highlightRegion() {
        highlightRegion(appRegion);
    }

    public void highlightRegion(Region region) {
        region.highlight(2, "green");
    }

    public void dragDrop(Dimension dimension) {
        this.dragDrop(dimension.width, dimension.height, appRegion);
    }

    public static void dragDrop(int w, int h, Region region) {
        if (w == 0 && h == 0) {
            return;
        }
        log.finer(format("dragDrop() w: %s, h: %s, region: %s", w, h, region));
        // Das drag-n-drop muss in umgekehrter Richtung erfolgen.
        h = h * -1;
        w = w * -1;
        Location sourceLocation = LocationMath.calculateSourceLocation(w, h, region);
        Location targetLocation = LocationMath.calculateTargetLocation(w, h, sourceLocation);
        try {
            region.dragDrop(sourceLocation, targetLocation);
        } catch (FindFailed e) {
            log.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    public void dragDrop(Dimension dimension, Region region) {
        this.dragDrop(dimension.width, dimension.height, region);
    }

    public void dragDrop(int w, int h) {
        dragDrop(w, h, appRegion);
    }

    public void doubleClick(Location location) {
        try {
            appRegion.doubleClick(location);
        } catch (FindFailed findFailed) {
            throw new RuntimeException(findFailed);
        }
    }
}
