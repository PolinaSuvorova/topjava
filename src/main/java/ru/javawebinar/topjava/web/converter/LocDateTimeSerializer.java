package ru.javawebinar.topjava.web.converter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import ru.javawebinar.topjava.util.DateTimeUtil;

import java.io.IOException;
import java.time.LocalDateTime;

public class LocDateTimeSerializer extends JsonSerializer<LocalDateTime> {
    @Override
    public void serialize(LocalDateTime value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeString(DateTimeUtil.toString(value));
    }
}
