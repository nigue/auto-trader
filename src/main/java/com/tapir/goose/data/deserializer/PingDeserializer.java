package com.tapir.goose.data.deserializer;

import com.tapir.goose.data.dto.PingDTO;
import jakarta.json.bind.serializer.DeserializationContext;
import jakarta.json.bind.serializer.JsonbDeserializer;
import jakarta.json.stream.JsonParser;

import java.lang.reflect.Type;

public class PingDeserializer implements JsonbDeserializer<PingDTO> {

    @Override
    public PingDTO deserialize(JsonParser jsonParser,
                               DeserializationContext deserializationContext,
                               Type type) {
        return new PingDTO();
    }
}
