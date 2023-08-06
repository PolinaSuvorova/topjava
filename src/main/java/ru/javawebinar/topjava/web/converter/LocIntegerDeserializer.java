package ru.javawebinar.topjava.web.converter;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;

public class LocIntegerDeserializer extends JsonDeserializer<Integer> {
    @Override
    public Integer deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
        String dateStr = p.getValueAsString();
        if (dateStr != null && dateStr != "") {

            return Integer.parseInt(dateStr);
        }
        return 0;
    }
}
