package de.puettner.sikuli.dso.adv;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import lombok.extern.java.Log;

import java.io.IOException;

@Log
public class AttackCampDeserializer extends StdDeserializer<AttackCamp> {

    protected AttackCampDeserializer() {
        super(AttackCamp.class);
    }

    //    protected AttackCampDeserializer(Class<?> vc) {
    //        super(vc);
    //    }

    @Override
    public AttackCamp deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        return BraveTailorAttackCamp.valueOf(p.getText());
    }
}
