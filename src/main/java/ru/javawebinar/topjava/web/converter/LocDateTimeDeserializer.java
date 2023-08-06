package ru.javawebinar.topjava.web.converter;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import ru.javawebinar.topjava.util.DateTimeUtil;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {
    @Override
    public LocalDateTime deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
       String dateStr = p.getValueAsString();
        if (dateStr != null) {

            return LocalDateTime.parse(dateStr, DateTimeFormatter.ISO_DATE_TIME);
        }
        return null;
    }
}
