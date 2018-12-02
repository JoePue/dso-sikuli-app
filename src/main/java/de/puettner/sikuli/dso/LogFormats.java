package de.puettner.sikuli.dso;

import org.sikuli.script.Location;

import java.awt.*;

public class LogFormats {

    public static String formatToLog(Dimension dimension) {
        return formatToLog(null, dimension);
    }

    public static String formatToLog(String name, Dimension dimension) {
        if (name == null) {
            name = "Dim";
        }
        return String.format("%s(%s, %s): ", name, dimension.width, dimension.height);
    }

    public static String formatToLog(Location location) {
        return formatToLog(null, location);
    }

    public static String formatToLog(String name, Location location) {
        if (name == null) {
            name = "Loc";
        }
        return String.format("%s(%s, %s): ", name, location.x, location.y);
    }
}
