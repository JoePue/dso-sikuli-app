package de.puettner.sikuli.dso.commands.ui;

import lombok.Builder;
import lombok.Data;
import org.sikuli.script.Pattern;

/**
 * Kapselt die Verbindung von Materialvorkommen mit ihren Sektoren
 */
@Builder
@Data
public class MaterialSector {
    public final Pattern pattern;
    public final Sector[] sectors;
}
