package de.puettner.sikuli.dso;

import de.puettner.sikuli.dso.commands.ui.MaterialType;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class GeologicLaunch {
    private GeologicType type;
    private MaterialType material;
    private int launchLimit;

    GeologicLaunch(GeologicType type, MaterialType material, int launchLimit) {
        this.type = type;
        this.material = material;
        this.launchLimit = launchLimit;
    }
}
