package de.puettner.sikuli.dso;

import de.puettner.sikuli.dso.commands.ui.MaterialType;
import de.puettner.sikuli.dso.commands.ui.StarMenuFilter;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.Iterator;

@Builder
@Data
public class GeologicLaunchs implements Iterable<GeologicLaunch> {

    private final ArrayList<GeologicLaunch> lanches = new ArrayList<>();

    public GeologicLaunchs add(GeologicType type, MaterialType material, int launchLimit) {
        this.lanches.add(new GeologicLaunch(type, material, launchLimit, StarMenuFilter.ALL));
        return this;
    }

    public GeologicLaunchs add(GeologicType type, MaterialType material, int launchLimit, StarMenuFilter filter) {
        this.lanches.add(new GeologicLaunch(type, material, launchLimit, filter));
        return this;
    }


    @Override
    public Iterator<GeologicLaunch> iterator() {
        return lanches.iterator();
    }
}
