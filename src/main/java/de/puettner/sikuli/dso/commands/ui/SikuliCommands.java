package de.puettner.sikuli.dso.commands.ui;

import lombok.extern.java.Log;
import org.sikuli.script.*;

import java.util.Iterator;
import java.util.logging.Level;

@Log
public class SikuliCommands {

    public static final int DEFAULT_WAITING = 500;
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
    public void sleep(int ms) {
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
     * Using type() with Special Characters or non QWERTY US keyboards https://answers.launchpad.net/sikuli/+faq/933
     */
    public int type(String text, String modifiers) {
        String str = text.toString().replace(':', ' ').replace('|', ' ');
        if (modifiers == null) {
            return appRegion.type(text.toString());
        }
        return appRegion.type(text.toString(), modifiers);
    }

    public int typeKeyDown() {
        return this.type(Key.DOWN.toString(), null);
    }

    public int type(Object input) {
        return this.type(input.toString(), null);
    }

    public Iterator<Match> findAll(Pattern pattern) {
        return this.findAll(pattern, appRegion);
    }

    Iterator<Match> findAll(Pattern pattern, Region searchRegion) {
        log.info("findAll");
        if (pattern == null || searchRegion == null) {
            throw new IllegalArgumentException(pattern + " " + searchRegion);
        }
        try {
            return searchRegion.findAll(pattern);
            //            Match match = searchRegion.find(pattern);
            //            if (match == null) {
            //                log.info("Nothing found.");
            //            } else {
            //                log.info("Found");
            //            }
            //            return Collections.emptyListIterator();
        } catch (FindFailed e) {
            throw new RuntimeException(e);
        }
        //        return Collections.emptyListIterator();
    }

    private String str(Object o) {
        return o.toString();
    }

    private void print(Object o) {
        log.info(o.toString());
    }

    public void focusBrowser() {
        app.focus(1);
        sleep();
    }

    public void sleep() {
        this.sleep(DEFAULT_WAITING);
    }

    public Location clickDsoTab() {
        Location rv = Commands.click("DSOTabIcon.png");
        sleep();
        return rv;
    }

    public Location parkMouse() {
        return org.sikuli.script.Commands.hover(new Location(appRegion.w / 2, appRegion.h + 50));
    }

    public boolean closeMenu() {
        return this.clickIfExists(pattern("Close-icon.png"), appRegion);
    }

    boolean clickIfExists(Pattern pattern, Region searchRegion) {
        final Match match = searchRegion.exists(pattern);
        boolean rv = false;
        if (match != null) {
            match.click();
            sleep(250);
            rv = true;
        }
        log.info(pattern.getFilename() + (rv ? " exists and clicked" : " not exists and not clicked"));
        return rv;
    }

    public static Pattern pattern(String filename) {
        Image img = Image.create(filename);
        return new Pattern(img);
    }

    boolean click(Pattern filename) {
        if (clickIfExists(filename, appRegion)) {
            return true;
        }
        log.log(Level.SEVERE, "Missing element");
        return false;
    }

    public void focusApp() {
        app.focus(1);
    }

    boolean exists(Pattern filename) {
        return this.exists(filename, appRegion);
    }

    boolean exists(Pattern pattern, Region searchRegion) {
        final Match match = find(pattern, searchRegion);
        boolean exists = (match == null ? false : true);
        log.fine(pattern.getFilename() + " " + (exists ? "exists" : "not exists"));
        return exists;
    }

    public Match find(Pattern filename, Region searchRegion) {
        final Match match = searchRegion.exists(filename);
        return match;
    }

    public void hightlightRegions() {
        appRegion.highlight(2, "green");
    }

    public void dragNdrop(int xOffset, int yOffset) {
        int xSource = appRegion.w - 100, ySource = appRegion.h - 100;
        if (yOffset < 0) {
            ySource = appRegion.h - 100;
        }
        if (xOffset < 0) {
            xSource = appRegion.w - 100;
        }
        Location sourceLocation = new Location(xSource, ySource);
        Location targetLocation = new Location(sourceLocation.x - xOffset, sourceLocation.y + yOffset);
        try {
            appRegion.dragDrop(sourceLocation, targetLocation);
        } catch (FindFailed e) {
            log.log(Level.SEVERE, e.getMessage(), e);
        }
    }

}
