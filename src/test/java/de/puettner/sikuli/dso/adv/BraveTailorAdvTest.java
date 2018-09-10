package de.puettner.sikuli.dso.adv;

import de.puettner.sikuli.dso.ui.commands.MenuTest;
import lombok.extern.java.Log;
import org.junit.Before;
import org.junit.Test;
import org.sikuli.script.Location;
import org.sikuli.script.Match;

@Log
public class BraveTailorAdvTest extends MenuTest {

    private final BraveTailorAdv adventure = AdventureBuilder.build().buildBraveTailorAdv();

    @Before
    public void before() {
        sikuliCmd.focusBrowser();
    }

    @Test
    public void play() {
        adventure.play();
    }

    @Test
    public void attack() {
        adventure.attack(BraveTailorAttackCamp.CAMP_4);
    }

    @Test
    public void campDragNDrop() {
        BraveTailorAttackCamp camp = BraveTailorAttackCamp.CAMP_2;
        islandCmds.dragDrop(camp.getDragNDrop().x, camp.getDragNDrop().y);
    }

    @Test
    public void attackCamp4() {
        adventure.attackCamp4();
    }

    @Test
    public void attackCamp5() {
        adventure.attackCamp5();
    }

    @Test
    public void attackCamp6() {
        adventure.attackCamp6();
    }

    @Test
    public void moveToSector2() {
        adventure.moveToSector2();
    }

    @Test
    public void findMovePoint() {
        Location moveOffset = new Location(-50, 220);
        BraveTailorAttackCamp movePoint = BraveTailorAttackCamp.MOVE_POINT_1;
        Match match = islandCmds.find(movePoint.getPattern(), islandCmds.getIslandRegion());
        if (match == null) {
            log.warning("Move point not found");
        }
        match = islandCmds.find(movePoint.getPattern(), islandCmds.getIslandRegion());
        if (match == null) {
            throw new IllegalStateException("Move point not found");
        }
        match.hover();
        islandCmds.hover(new Location(match.x + (match.w / 2) + moveOffset.x, match.y + (match.h / 2) + moveOffset.y));
        // Find ref point and place Gen
    }

    @Test
    public void attackCampOfSector2() {
        //        adventure.attackCamp7ofSector2();
        adventure.attackCamp8ofSector2();
    }


}
