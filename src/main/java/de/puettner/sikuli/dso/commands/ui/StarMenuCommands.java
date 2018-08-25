package de.puettner.sikuli.dso.commands.ui;

import lombok.extern.slf4j.Slf4j;
import org.sikuli.script.Key;
import org.sikuli.script.Match;
import org.sikuli.script.Pattern;
import org.sikuli.script.Region;

@Slf4j
public class StarMenuCommands extends MenuCommands {

    protected StarMenuCommands(Region menuRegion, SikuliCommands sikuliCmds) {
        super(menuRegion, sikuliCmds);
    }

    public void openBuildMenu() {
        sikuliCmds.click(SikuliCommands.pattern("BuildMenuBarButton.png").similar(0.90f));
    }

    public int launchAllExplorerByImage(Pattern image) {
        log.info("launchAllExplorerByImage");
        int launchCount = 0;
        do {
            openStarMenu();
            Match match = sikuliCmds.find(image, menuRegion);
            if (match != null) {
                launchExplorer(match, menuRegion);
                sikuliCmds.parkMouse();
                launchCount++;
                sikuliCmds.sleep(1);
            } else {
                log.info("No explorer found. launchCount: " + launchCount);
                break;
            }
        } while (true);
        return launchCount;
    }

    public boolean openStarMenu() {
        return this.openStarMenu(null);
    }

    public <PSI> boolean launchExplorer(Match match, Region searchRegion) {
        log.info("launchExplorer()");
        match.click();
        sikuliCmds.click(SikuliCommands.pattern("TreasureFind-icon.png").targetOffset(49, -1));
        sikuliCmds.click(SikuliCommands.pattern("TreasureSearchVeryLong.png").targetOffset(53, 0));
        sikuliCmds.clickSmallOkButton();
        return true;
    }

    public boolean openStarMenu(@Deprecated String searchString) {
        log.info("openStarMenu()" + (searchString == null ? "" : "searchString: " + searchString));
        if (!isStarMenuOpen()) {
            sikuliCmds.clickStarMenuButton();
            sikuliCmds.sleep(1);
        }
        if (searchString != null) {
            sikuliCmds.clickIfExists(SikuliCommands.pattern("zoom-icon.png").targetOffset(-43, -3), menuRegion);
            sikuliCmds.sleep(1);
            sikuliCmds.type("a", Key.CTRL);
            sikuliCmds.sleep(1);
            sikuliCmds.paste(searchString);
        }
        sikuliCmds.parkMouse();
        return true;
    }

    public boolean isStarMenuOpen() {
        if (sikuliCmds.exists("StarMenu-Button.png", menuRegion)) {
            log.info("StarMenu is open");
            return true;
        }
        log.info("StarMenu is NOT open");
        return false;
    }

    public <PSI> int launchAllGeologicsByImage(PSI image, MaterialType material, int launchLimit) {
        log.info("launchAllGeologicsByImage");
        int launchCount = 0;
        for (int i = 0; i <= launchLimit; ++i) {
            openStarMenu();
            Match match = sikuliCmds.find(image, menuRegion);
            if (match != null) {
                if (launchGeologic(match, material)) {
                    launchCount++;
                    sikuliCmds.parkMouse();
                    sikuliCmds.sleep(1);
                }
            } else {
                log.info("No Geologic launched");
                break;
            }
        }
        return launchCount;
    }

    // ST KU MA EI KO GO GR TI SA
    public boolean launchGeologic(Match match, MaterialType material) {
        log.info("launchGeologic material: " + material);
        if (match.click() == 1) {
            if (MaterialType.ST.equals(material)) {
                sikuliCmds.clickIfExists("Material-Stone-Button.png", menuRegion);
            } else if (MaterialType.MA.equals(material)) {
                sikuliCmds.clickIfExists("Material-Marble-Button.png", menuRegion);
            } else if (MaterialType.GR.equals(material)) {
                sikuliCmds.clickIfExists("Material-Granite-Button.png", menuRegion);
            } else if (MaterialType.KU.equals(material)) {
                sikuliCmds.clickIfExists("Material-Copper-Button.png", menuRegion);
            } else if (MaterialType.EI.equals(material)) {
                sikuliCmds.clickIfExists("Material-Iron-Button.png", menuRegion);
            } else {
                throw new IllegalArgumentException("Unsupported type: " + material);
            }
            return sikuliCmds.clickSmallOkButton();
        }
        return false;
    }

    public boolean closeStarMenu() {
        boolean rv = false;
        if (isStarMenuOpen()) {
            log.info("closeStarMenu");
            sikuliCmds.typeESC();
            rv = true;
        } else {
            log.info("closeStarMenu : Not open");
        }
        return rv;
    }
}
