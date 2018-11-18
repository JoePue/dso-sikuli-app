package de.puettner.sikuli.dso.adv;

/**
 * Die Koordinaten der Eingabefelder werden relativ zum Rekruten Icon angegeben.
 */
public enum AttackUnitType {
    Rek(70, 20), Bos(190, 20), Mil(320, 20),
    Cav(70, 75), Lnb(190, 75), Sol(320, 75),
    Arm(70, 130), Eso(190, 130), Kan(320, 130);

    final int xOffset;
    final int yOffset;

    AttackUnitType(int xOffset, int yOffset) {
        this.xOffset = xOffset;
        this.yOffset = yOffset;
    }

}
