package de.puettner.sikuli.dso.commands.ui;

import lombok.extern.java.Log;
import org.sikuli.script.Key;
import org.sikuli.script.Match;
import org.sikuli.script.Region;

import java.util.Optional;

import static de.puettner.sikuli.dso.commands.ui.SikuliCommands.pattern;
import static java.util.logging.Level.WARNING;

@Log
public class StarMenu extends DsoMenu {

    private String lastFilterString = null;

    protected StarMenu(Region menuRegion, IslandCommands islandCmds) {
        super(menuRegion, islandCmds);
    }

    public boolean openBuildMenu() {
        boolean rv = islandCmds.click(pattern("BuildMenuBarButton.png").similar(0.90f));
        islandCmds.sleep();
        return rv;
    }

    public int launchAllExplorerByImage(ExplorerType explorer, ExplorerAction action, ExplorerActionType type) {
        log.info("launchAllExplorerByImage");
        int launchCount = 0;
        int maxLoops = 4;
        do {
            openStarMenu(Optional.empty());
            Match match = islandCmds.find(explorer.getPattern(), menuRegion);
            if (match != null) {
                if (!launchExplorer(match, menuRegion, action, type)) {
                    log.log(WARNING, "No explorer launched");
                    break;
                }
                launchCount++;
                islandCmds.parkMouse();
                islandCmds.sleep();
            } else {
                log.info("No explorer found. launchCount: " + launchCount);
                break;
            }
            if (launchCount > maxLoops) {
                break;
            }
        } while (true);
        return launchCount;
    }

    public boolean openStarMenu(Optional<StarMenuFilter> menuFilter) {
        String str = null;
        if (menuFilter.isPresent()) {
            str = menuFilter.get().filterString;
        }
        return this.openStarMenu(str);
    }

    public <PSI> boolean launchExplorer(Match match, Region searchRegion, ExplorerAction action, ExplorerActionType type) {
        log.info("launchExplorer()");
        match.click();
        islandCmds.sleep();
        if (!islandCmds.click(action.getPattern())) {
            return false;
        }
        islandCmds.sleep();
        if (!islandCmds.click(type.getPattern())) {
            return false;
        }
        islandCmds.sleep();
        if (!islandCmds.clickSmallOkButton()) {
            return false;
        }
        return true;
    }

    public boolean openStarMenu(String filterString) {
        log.info("openStarMenu() " + (filterString == null ? "" : "menuFilter: " + filterString));
        boolean rv = true;
        if (!isStarMenuOpen()) {
            if (islandCmds.clickStarButton()) {
                waitUntilExists(this::isStarMenuOpen);
            } else {
                log.severe("Required click failed");
            }
        }
        if (isStarMenuOpen()) {
            // Zur Sicherheit immer das Stern-Tab anklicken
            super.clickIfExists(StarMenuButtons.StarMenuStarTab);

            if (filterString != null) {
                if (lastFilterString == null || !lastFilterString.equals(filterString)) {
                    lastFilterString = filterString;
                    if (super.clickIfExists(StarMenuButtons.ZOOM_ICON)) {
                        islandCmds.sleep();
                        islandCmds.type("a", Key.CTRL);
                        islandCmds.sleep();
                        islandCmds.paste(filterString);
                    } else {
                        throw new IllegalStateException("Zoom Icon must exists.");
                    }
                } else {
                    log.info("Set no filter string because the current one '" + filterString + "' is equals to the last one '" +
                            lastFilterString + "'");
                }
            }
        } else {
            log.severe("Required 'open star menu' is missing");
            rv = false;
        }
        islandCmds.parkMouse();
        return rv;
    }

    public boolean isStarMenuOpen() {
        if (islandCmds.exists(StarMenuButtons.StarMenuTitleOrnament.pattern, menuRegion)) {
            log.info("StarMenu is open");
            return true;
        }
        log.info("StarMenu is NOT open");
        return false;
    }

    public <PSI> int launchAllGeologicsByImage(MenuButton menuButton, MaterialType material, int launchLimit, StarMenuFilter filter) {
        log.info("launchAllGeologicsByImage");
        int launchCount = 0;
        for (int i = 0; i < launchLimit; ++i) {
            if (!openStarMenu(Optional.ofNullable(filter))) {
                break;
            }
            Match match = islandCmds.find(menuButton.getPattern(), menuRegion);
            if (match != null) {
                islandCmds.sleep();
                if (launchGeologic(match, material)) {
                    launchCount++;
                    islandCmds.parkMouse();
                    islandCmds.sleep();
                }
            } else {
                log.info("No Geologic launched");
                break;
            }
        }
        return launchCount;
    }

    /**
     * @param match    gefundener geoloage
     * @param material
     * @return
     */
    public boolean launchGeologic(Match match, MaterialType material) {
        log.info("launchGeologic material: " + material);
        if (match.click() == 1) {
            islandCmds.sleep();
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
            } else if (MaterialType.GO.equals(material)) {
                islandCmds.clickIfExists(pattern("Material-Gold-Button.png").similar(0.80f), menuRegion);
            } else if (MaterialType.KO.equals(material)) {
                islandCmds.clickIfExists(pattern("Material-Cole-Button.png").similar(0.80f), menuRegion);
            } else {
                throw new IllegalArgumentException("Unsupported stepType: " + material);
            }
            islandCmds.sleep();
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

    public boolean openMessageBox() {
        log.info("openMessageBox()");
        boolean rv = true;
        rv = clickMessageBoxButton();
        if (rv) {
            islandCmds.parkMouse();
            islandCmds.sleepX(5);
        }
        return rv;
    }

    public boolean clickMessageBoxButton() {
        log.info("clickStarButton");
        return islandCmds.clickIfExists(pattern("Message-Box-Button.png").similar(0.90f).targetOffset(-1, -6), menuRegion);
    }

    public boolean clickOkBuildingButton() {
        return islandCmds.clickOkButton(MessageBoxButton.OK_BUILDING);
    }

    public boolean clickOkStarButton() {
        return islandCmds.clickOkButton(MessageBoxButton.OK_STAR);
    }
}
