package de.puettner.sikuli.dso.adv;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.awt.*;
import java.util.Objects;

@Data
//@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "className")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AdventureStep {

    private int no;
    private StepType stepType;
    private AdventureStepState state;
    /** Delay before step execution */
    private Integer delay;
    private GeneralType general;
    /** name of the general used as search string in star menu */
    private String generalName;
    private AttackCamp camp;
    private AttackUnit[] units;
    private NavigationPoint startNavPoint;
    /** Diese Property ist nur bei 'stepType == MOVE' gesetzt. */
    private NavigationPoint targetNavPoint;
    /** Mittels diesem Drag-n-Drop soll zum zugehörigen NavPoint sich bewegt werden */
    private Dimension initialDragDropOffset;
    /** Diese Property ist nur bei 'stepType == MOVE' gesetzt. */
    private Dimension targetDragDropOffset;
    /** Erforderlich für den MOVE-Step in Sektor 1 vom Landeplatz zur ersten Platzierung */
    private Dimension targetNavPointClickOffset;
    private String comment;

    protected AdventureStep() {
    }

    public AdventureStep(StepType stepType, NavigationPoint startNavPoint, NavigationPoint targetNavPoint, AttackCamp camp,
                         GeneralType general, String generalName, Integer delay,
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
