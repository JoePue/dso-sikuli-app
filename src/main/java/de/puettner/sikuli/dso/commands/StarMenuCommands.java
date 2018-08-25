package de.puettner.sikuli.dso.commands;

import lombok.extern.slf4j.Slf4j;
import org.sikuli.script.Key;
import org.sikuli.script.Match;
import org.sikuli.script.Pattern;
import org.sikuli.script.Region;

import static de.puettner.sikuli.dso.commands.SikuliCommands.pattern;

@Slf4j
public class StarMenuCommands {

    private final SikuliCommands sikuliCommands;
    private Region menuRegion;

    protected StarMenuCommands(Region menuRegion, SikuliCommands sikuliCommands) {
        this.menuRegion = menuRegion;
        this.sikuliCommands = sikuliCommands;
    }

    public void openBuildMenu() {
        sikuliCommands.click(pattern("BuildMenuBarButton.png").similar(0.90f));
    }

    public <PSI> boolean launchExplorer(Match match, Region searchRegion) {
        log.info("launchExplorer()");
        match.click();
        sikuliCommands.click(pattern("TreasureFind-icon.png").targetOffset(49, -1));
        sikuliCommands.click(pattern("TreasureSearchVeryLong.png").targetOffset(53, 0));
        sikuliCommands.clickSmallOkButton();
        return true;
    }

    public int launchAllExplorerByImage(Pattern image) {
        log.info("launchAllExplorerByImage");
        int launchCount = 0;
        do {
            openStarMenu();
            Match match = sikuliCommands.find(image, menuRegion);
            if (match != null) {
                launchExplorer(match, menuRegion);
                sikuliCommands.parkMouse();
                launchCount++;
                sikuliCommands.sleep(1);
            } else {
                log.info("No explorer found. launchCount: " + launchCount);
                break;
            }
        } while (true);
        return launchCount;
    }

    // ST KU MA EI KO GO GR TI SA
    public boolean launchGeologic(Match match, MaterialType material) {
        log.info("launchGeologic material: " + material);
        if (match.click() == 1) {
            if (MaterialType.ST.equals(material)) {
                sikuliCommands.clickIfExists("Material-Stone-Button.png", menuRegion);
            } else if (MaterialType.MA.equals(material)) {
                sikuliCommands.clickIfExists("Material-Marble-Button.png", menuRegion);
            } else if (MaterialType.GR.equals(material)) {
                sikuliCommands.clickIfExists("Material-Granite-Button.png", menuRegion);
            } else if (MaterialType.KU.equals(material)) {
                sikuliCommands.clickIfExists("Material-Copper-Button.png", menuRegion);
            } else if (MaterialType.EI.equals(material)) {
                sikuliCommands.clickIfExists("Material-Iron-Button.png", menuRegion);
            } else {
                throw new IllegalArgumentException("Unsupported type: " + material);
            }
            return sikuliCommands.clickSmallOkButton();
        }
        return false;
    }

    public <PSI> int launchAllGeologicsByImage(PSI image, MaterialType material, int launchLimit) {
        log.info("launchAllGeologicsByImage");
        int launchCount = 0;
        for (int i = 0; i <= launchLimit; ++i) {
            openStarMenu();
            Match match = sikuliCommands.find(image, menuRegion);
            if (match != null) {
                if (launchGeologic(match, material)) {
                    launchCount++;
                    sikuliCommands.parkMouse();
                    sikuliCommands.sleep(1);
                }
            } else {
                log.info("No Geologic launched");
                break;
            }
        }
        return launchCount;
    }


    public boolean isStarMenuOpen() {
        if (sikuliCommands.exists("StarMenu-Button.png", menuRegion)) {
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
            sikuliCommands.clickStarMenuButton();
            sikuliCommands.sleep(1);
        }
        if (searchString != null) {
            sikuliCommands.clickIfExists(pattern("zoom-icon.png").targetOffset(-43, -3), menuRegion);
            sikuliCommands.sleep(1);
            sikuliCommands.type("a", Key.CTRL);
            sikuliCommands.sleep(1);
            sikuliCommands.paste(searchString);
        }
        sikuliCommands.parkMouse();
        return true;
    }

    public boolean closeStarMenu() {
        boolean rv = false;
        if (isStarMenuOpen()) {
            log.info("closeStarMenu");
            sikuliCommands.typeESC();
            rv = true;
        } else {
            log.info("closeStarMenu : Not open");
        }
        return rv;
    }
}
