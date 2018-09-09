package de.puettner.sikuli.dso.adv;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class AttackUnit {
    private AttackUnitType type;
    private int quantity;
}
