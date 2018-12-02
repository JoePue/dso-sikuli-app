package de.puettner.sikuli.dso.adv.step_proc;

import de.puettner.sikuli.dso.adv.*;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static de.puettner.sikuli.dso.adv.AdventureStepState.DONE;
import static de.puettner.sikuli.dso.adv.AdventureStepState.OPEN;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class AttackStepProcessorTest {

    AttackStepProcessor sut;

    @Before
    public void before() {
        sut = AdventureBuilder.build().buildBraveTailorAdv().getAttackStepProcessor();
    }

    @Test
    public void isPreparationRequiredWithIdenticalStepInPast() throws Exception {
        List<AdventureStep> steps = new ArrayList<>();
        steps.add(buildStep(StepType.ATTACK, DONE, 1));
        steps.add(buildStep(StepType.ATTACK, OPEN, 1));
        AdventureStep step = steps.get(1);

        boolean result = sut.isPreparationRequired(steps, steps.size() - 1, false);
        assertFalse(result);
    }

    private AdventureStep buildStep(StepType type, AdventureStepState state, int quantity) {
        AdventureStep step = new AdventureStep();
        step.setGeneral(GeneralType.MdK);
        step.setGeneralName("MdK 1");
        step.setStepType(type);
        step.setState(state);
        AttackUnit[] units = new AttackUnit[]{AttackUnit.Rek(quantity)};
        step.setUnits(units);
        return step;
    }

    @Test
    public void isPreparationRequiredWithNonIdenticalStepInPast() throws Exception {
        List<AdventureStep> steps = new ArrayList<>();
        steps.add(buildStep(StepType.ATTACK, DONE, 2));
        steps.add(buildStep(StepType.ATTACK, OPEN, 1));
        AdventureStep step = steps.get(1);

        boolean result = sut.isPreparationRequired(steps, steps.size() - 1, false);
        assertTrue(result);
    }

    @Test
    public void isPreparationRequiredWithUnsetStepInPast() throws Exception {
        List<AdventureStep> steps = new ArrayList<>();
        steps.add(buildStep(StepType.ATTACK, DONE, 1));
        steps.add(buildStep(StepType.UNSET_UNITS, DONE, 1));
        steps.add(buildStep(StepType.MOVE, DONE, 1));
        steps.add(buildStep(StepType.ATTACK, OPEN, 1));

        boolean result = sut.isPreparationRequired(steps, steps.size() - 1, false);
        assertTrue(result);
    }


}
