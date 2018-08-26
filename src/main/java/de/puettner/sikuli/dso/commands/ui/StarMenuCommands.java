package de.puettner.sikuli.dso.commands.ui;

import lombok.extern.slf4j.Slf4j;
import org.sikuli.script.Key;
import org.sikuli.script.Match;
import org.sikuli.script.Pattern;
import org.sikuli.script.Region;

import static de.puettner.sikuli.dso.commands.ui.SikuliCommands.pattern;

@Slf4j
public class StarMenuCommands extends MenuCommands {

    protected StarMenuCommands(Region menuRegion, IslandCommands islandCmds) {
        super(menuRegion, islandCmds);
    }

    public void openBuildMenu() {
        islandCmds.click(pattern("BuildMenuBarButton.png").similar(0.90f));
        islandCmds.sleep(1);
    }

    public int launchAllExplorerByImage(Pattern image) {
        log.info("launchAllExplorerByImage");
        int launchCount = 0;
        do {
            openStarMenu();
            Match match = islandCmds.find(image, menuRegion);
            if (match != null) {
                launchExplorer(match, menuRegion);
                islandCmds.parkMouse();
                launchCount++;
                islandCmds.sleep(1);
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
        islandCmds.click(pattern("TreasureFind-icon.png").targetOffset(49, -1));
        islandCmds.click(pattern("TreasureSearchVeryLong.png").targetOffset(53, 0));
        islandCmds.clickSmallOkButton();
        return true;
    }

    public boolean openStarMenu(String searchString) {
        log.info("openStarMenu()" + (searchString == null ? "" : "searchString: " + searchString));
        if (!isStarMenuOpen()) {
            islandCmds.clickStarButton();
            islandCmds.sleep(2);
        }
        if (searchString != null) {
            islandCmds.clickIfExists(StarMenuButtons.ZOOM_ICON.pattern, menuRegion);
            islandCmds.sleep(1);
            islandCmds.type("a", Key.CTRL);
            islandCmds.sleep(1);
            islandCmds.paste(searchString);
        }
        islandCmds.parkMouse();
        return true;
    }

    public boolean isStarMenuOpen() {
        if (islandCmds.exists(StarMenuButtons.StarMenuTitleImage.pattern, menuRegion)) {
            log.info("StarMenu is open");
            return true;
        }
        log.info("StarMenu is NOT open");
        return false;
    }

    public <PSI> int launchAllGeologicsByImage(MenuButton image, MaterialType material, int launchLimit) {
        log.info("launchAllGeologicsByImage");
        int launchCount = 0;
        for (int i = 0; i <= launchLimit; ++i) {
            if (!openStarMenu()) {
                break;
            }
            Match match = islandCmds.find(image.getPattern(), menuRegion);
            if (match != null) {
                if (launchGeologic(match, material)) {
                    launchCount++;
                    islandCmds.parkMouse();
                    islandCmds.sleep(1);
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
                islandCmds.clickIfExists(pattern("Material-Stone-Button.png"), menuRegion);
            } else if (MaterialType.MA.equals(material)) {
                islandCmds.clickIfExists(pattern("Material-Marble-Button.png"), menuRegion);
            } else if (MaterialType.GR.equals(material)) {
                islandCmds.clickIfExists(pattern("Material-Granite-Button.png"), menuRegion);
            } else if (MaterialType.KU.equals(material)) {
                islandCmds.clickIfExists(pattern("Material-Copper-Button.png"), menuRegion);
            } else if (MaterialType.EI.equals(material)) {
                islandCmds.clickIfExists(pattern("Material-Iron-Button.png"), menuRegion);
            } else {
                throw new IllegalArgumentException("Unsupported type: " + material);
            }
            return islandCmds.clickSmallOkButton();
        }
        return false;
    }

    public boolean closeStarMenu() {
        boolean rv = false;
        if (isStarMenuOpen()) {
            log.info("closeStarMenu");
            islandCmds.typeESC();
            rv = true;
        } else {
            log.info("closeStarMenu : Not open");
        }
        return rv;
    }
}
