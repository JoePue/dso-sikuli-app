package de.puettner.sikuli.dso.adv;

import org.junit.Test;

import static de.puettner.sikuli.dso.adv.AdventureStepState.OPEN;
import static de.puettner.sikuli.dso.adv.AdventureStepState.PREPARED;
import static de.puettner.sikuli.dso.adv.StepType.ATTACK;
import static de.puettner.sikuli.dso.adv.StepType.MOVE;
import static junit.framework.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class AdventureTest {

    Adventure sut = AdventureBuilder.build().buildBraveTailorAdv();

    @Test
    public void isDelay() {
        AdventureStep step = buildStep(ATTACK, PREPARED);
        assertFalse(sut.isDelay(true, step));

        step = buildStep(MOVE, OPEN);
        assertTrue(sut.isDelay(false, step));

        step = buildStep(ATTACK, OPEN);
        assertFalse(sut.isDelay(false, step));

        step = buildStep(ATTACK, PREPARED);
        assertTrue(sut.isDelay(false, step));
    }

    private AdventureStep buildStep(StepType type, AdventureStepState state) {
        AdventureStep step = new AdventureStep();
        step.setState(state);
        step.setDelay(10);
        step.setStepType(type);
        return step;
    }
}
