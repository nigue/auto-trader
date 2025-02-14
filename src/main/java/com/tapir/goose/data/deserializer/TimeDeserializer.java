package com.tapir.goose.data.deserializer;

import com.tapir.goose.data.dto.TimeDTO;
import jakarta.json.bind.serializer.DeserializationContext;
import jakarta.json.bind.serializer.JsonbDeserializer;
import jakarta.json.stream.JsonParser;

import java.lang.reflect.Type;

public class TimeDeserializer implements JsonbDeserializer<TimeDTO> {

    @Override
    public TimeDTO deserialize(JsonParser jsonParser,
                               DeserializationContext deserializationContext,
                               Type type) {
        return new TimeDTO("asfd");
    }
}
