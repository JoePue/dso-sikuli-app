package de.puettner.sikuli.dso;

import de.puettner.sikuli.dso.commands.ui.MenuBuilder;
import de.puettner.sikuli.dso.commands.ui.Sector;
import org.junit.Test;

public class SectorServiceTest extends BaseServiceTest {

    private SectorService dsoService = new SectorService(MenuBuilder.build().buildIslandCommand());

    @Test
    public void goToSector() {
        dsoService.goToSector(Sector.S1);
        dsoService.goToSector(Sector.S10);
    }

    @Test
    public void visitAllSectors() {
        dsoService.visitAllSectors();
    }
}
