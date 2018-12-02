package de.puettner.sikuli.dso.adv;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AdventureConfiguration {

    /**
     * False bedeutet das ein General, der bereits mit einem Rekruten ein Zuweisung hatte,
     * nicht erneut die gleiche Einheitenzuweisung erhält.
     */
    private Boolean redoOneManSetup;
}
