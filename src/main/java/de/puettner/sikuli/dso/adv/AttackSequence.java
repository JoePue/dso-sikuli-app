package de.puettner.sikuli.dso.adv;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.LinkedList;
import java.util.Objects;

@Data
public class AttackSequence {

    private LinkedList<AttackStep> steps = new LinkedList();

    public AttackSequence add(GeneralType general, int delay, AttackUnit... units) {
        Objects.requireNonNull(general);
        Objects.requireNonNull(units);
        this.steps.add(new AttackStep(general, delay, units));
        return this;
    }

    @Data
    @AllArgsConstructor
    public class AttackStep {
        private GeneralType general;
        private int delay;
        private AttackUnit[] units;
    }

}
