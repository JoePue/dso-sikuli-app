package de.puettner.sikuli.dso.adv;

import lombok.Data;

import java.util.Objects;

@Data
//@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "className")
public class AdventureStep {

    private StepType stepType;
    private AdventureStepState state;
    /** Delay before step execution attack */
    private int delay;
    private AttackCamp camp;
    /** name of the general used as search string in star menu */
    private String generalName;
    private GeneralType general;
    /** Delay before attack */
    private AttackUnit[] units;
    private NavigationPoint startNavPoint;
    /** Diese Property ist nur bei 'stepType == MOVE' gesetzt. */
    private NavigationPoint targetNavPoint;

    protected AdventureStep() {

    }

    public AdventureStep(StepType stepType, NavigationPoint startNavPoint, NavigationPoint targetNavPoint, AttackCamp camp,
                         GeneralType general, String generalName, int delay,
                         AdventureStepState state, AttackUnit[] units) {
        this.stepType = stepType;
        this.delay = delay;
        state = AdventureStepState.OPEN;
        this.general = general;
        this.units = units;
        this.generalName = generalName;
        this.camp = camp;
        this.startNavPoint = startNavPoint;
        this.targetNavPoint = targetNavPoint;
    }

    public static AdventureStep buildAttackStep(NavigationPoint startNavPoint, AttackCamp camp, GeneralType general, String
            generalName, int delay, AdventureStepState state, AttackUnit... units) {
        Objects.requireNonNull(general);
        Objects.requireNonNull(units);
        AdventureStep step = new AdventureStep(StepType.ATTACK, startNavPoint, null, camp, general, generalName, delay,
                state, units);
        step.setState(state);
        return step;
    }

    public static AdventureStep buildMoveStep(NavigationPoint startNavPoint, NavigationPoint targetNavPoint, GeneralType general,
                                              String generalName, int delay, AdventureStepState state) {
        Objects.requireNonNull(startNavPoint);
        Objects.requireNonNull(targetNavPoint);
        Objects.requireNonNull(general);
        return new AdventureStep(StepType.MOVE, startNavPoint, targetNavPoint, null, general, generalName, delay, state, null);
    }
}
