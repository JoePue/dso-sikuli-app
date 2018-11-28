package de.puettner.sikuli.dso;

import de.puettner.sikuli.dso.commands.ui.MaterialType;
import de.puettner.sikuli.dso.commands.ui.Sector;
import de.puettner.sikuli.dso.commands.ui.SikuliCommands;
import lombok.extern.java.Log;

import java.awt.*;

@Log
public class SectorService {

    private final SikuliCommands islandCmds;

    public SectorService(SikuliCommands islandCmds) {this.islandCmds = islandCmds;}

    public void visitAllSectors() {
        log.info("visitAllSectors()");
        for (Sector sector : Sector.values()) {
            this.goToSector(sector);
        }
        goToSector(Sector.S1);
    }

    void goToSector(Sector sector) {
        log.info("goToSector() " + sector);
        int i = sector.index();
        if (i >= 0 && i <= 9) {
            islandCmds.type(i);
        } else {
            islandCmds.type(i - 9);
            if (i >= 10 && i <= 12) {
                islandCmds.dragDrop(300, 750);
            }
        }
        islandCmds.sleepX(2);
    }

    /**
     * Beim Minenbau müssen bei Drag-n-Drops durchgeführt werden, um die Rohstoffvorkommen zu finden.
     */
    void goToSector(Sector sector, MaterialType material) {
        this.goToSector(sector);
        if (Sector.S5.equals(sector)) {
            if (MaterialType.EI.equals(material)) {
                islandCmds.dragDrop(new Dimension(-250, -250));
            }
        } else if (Sector.S6.equals(sector)) {
            if (MaterialType.KO.equals(material)) {
                islandCmds.dragDrop(new Dimension(200, 0));
            }
        }
    }
}
