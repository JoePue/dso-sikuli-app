package de.puettner.sikuli.dso;

import de.puettner.sikuli.dso.commands.ui.MaterialType;
import de.puettner.sikuli.dso.commands.ui.StarMenuFilter;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class GeologicLaunch {
    private GeologicType type;
    private MaterialType material;
    private int launchLimit;
    private StarMenuFilter filter;

    public GeologicLaunch(GeologicType type, MaterialType material, int launchLimit, StarMenuFilter filter) {
        this.type = type;
        this.material = material;
        this.launchLimit = launchLimit;
        this.filter = filter;
    }
}
