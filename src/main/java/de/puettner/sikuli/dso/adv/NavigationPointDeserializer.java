package de.puettner.sikuli.dso.adv;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import lombok.extern.java.Log;

import java.io.IOException;

@Log
public class NavigationPointDeserializer extends StdDeserializer<NavigationPoint> {

    protected NavigationPointDeserializer() {
        super(NavigationPoint.class);
    }

    @Override
    public NavigationPoint deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        // TODO JPU quick and dirty
        return BraveTailorNavPoints.valueOf(p.getText());
    }
}
