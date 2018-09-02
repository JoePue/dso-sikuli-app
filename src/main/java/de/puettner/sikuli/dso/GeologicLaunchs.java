package de.puettner.sikuli.dso;

import de.puettner.sikuli.dso.commands.ui.MaterialType;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.Iterator;

@Builder
@Data
public class GeologicLaunchs implements Iterable<GeologicLaunch> {

    private final ArrayList<GeologicLaunch> lanches = new ArrayList<>();

    public GeologicLaunchs add(GeologicType type, MaterialType material, int launchLimit) {
        this.lanches.add(new GeologicLaunch(type, material, launchLimit));
        return this;
    }

    @Override
    public Iterator<GeologicLaunch> iterator() {
        return lanches.iterator();
    }
}
