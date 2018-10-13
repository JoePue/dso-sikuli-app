package de.puettner.sikuli.dso.adv;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsonFormatVisitors.*;

/**
 * Created by joerg.puettner on 13.10.2018.
 */
public class AttackUnitFormatVisitor implements JsonFormatVisitorWrapper {
    @Override
    public JsonObjectFormatVisitor expectObjectFormat(JavaType type) throws JsonMappingException {
        return null;
    }

    @Override
    public JsonArrayFormatVisitor expectArrayFormat(JavaType type) throws JsonMappingException {
        return null;
    }

    @Override
    public JsonStringFormatVisitor expectStringFormat(JavaType type) throws JsonMappingException {
        return null;
    }

    @Override
    public JsonNumberFormatVisitor expectNumberFormat(JavaType type) throws JsonMappingException {
        return null;
    }

    @Override
    public JsonIntegerFormatVisitor expectIntegerFormat(JavaType type) throws JsonMappingException {
        return null;
    }

    @Override
    public JsonBooleanFormatVisitor expectBooleanFormat(JavaType type) throws JsonMappingException {
        return null;
    }

    @Override
    public JsonNullFormatVisitor expectNullFormat(JavaType type) throws JsonMappingException {
        return null;
    }

    @Override
    public JsonAnyFormatVisitor expectAnyFormat(JavaType type) throws JsonMappingException {
        return null;
    }

    @Override
    public JsonMapFormatVisitor expectMapFormat(JavaType type) throws JsonMappingException {
        return null;
    }

    @Override
    public SerializerProvider getProvider() {
        return null;
    }

    @Override
    public void setProvider(SerializerProvider provider) {

    }
}
