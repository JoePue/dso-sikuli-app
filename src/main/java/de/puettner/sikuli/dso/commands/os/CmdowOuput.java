package de.puettner.sikuli.dso.commands.os;

import lombok.Builder;
import lombok.Data;
import org.sikuli.script.Region;

@Data
@Builder
public class CmdowOuput {
    private String handle;
    private long pid;
    private Region region;
}
