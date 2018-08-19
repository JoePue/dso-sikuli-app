package de.puettner.sikulie.dso;

import org.sikuli.script.FindFailed;

public class FindFailedRTException extends RuntimeException{
    public FindFailedRTException(FindFailed e) {
        super(e);
    }
}
