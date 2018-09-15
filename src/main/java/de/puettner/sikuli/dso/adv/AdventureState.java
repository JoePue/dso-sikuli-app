package de.puettner.sikuli.dso.adv;

import lombok.Data;

import java.util.List;

//@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "className")
@Data
public class AdventureState {
    private List<AdventureAttackStep> adventureSteps;
}
