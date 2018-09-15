package de.puettner.sikuli.dso.adv;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static de.puettner.sikuli.dso.adv.AttackUnitType.*;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AttackUnit {

    private AttackUnitType type;
    private int quantity;

    public static AttackUnit Rek(int i) {
        return AttackUnit.builder().type(Rek).quantity(i).build();
    }

    public static AttackUnit Bos(int i) {
        return AttackUnit.builder().type(Bos).quantity(i).build();
    }

    public static AttackUnit Cav(int i) {
        return AttackUnit.builder().type(Cav).quantity(i).build();
    }

    public static AttackUnit Lnb(int i) {
        return AttackUnit.builder().type(Lnb).quantity(i).build();
    }

    public static AttackUnit Sol(int i) {
        return AttackUnit.builder().type(Sol).quantity(i).build();
    }

    public static AttackUnit Arm(int i) {
        return AttackUnit.builder().type(Arm).quantity(i).build();
    }

    public static AttackUnit Kan(int i) {
        return AttackUnit.builder().type(Kan).quantity(i).build();
    }
}
