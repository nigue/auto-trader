package com.tapir.goose.data.deserializer;

import com.tapir.goose.data.dto.RateLimitDTO;
import jakarta.json.bind.serializer.DeserializationContext;
import jakarta.json.bind.serializer.JsonbDeserializer;
import jakarta.json.stream.JsonParser;

import java.lang.reflect.Type;

public class RateLimitDeserializer implements JsonbDeserializer<RateLimitDTO> {

    @Override
    public RateLimitDTO deserialize(JsonParser parser,
                                    DeserializationContext deserializationContext,
                                    Type type) {
        String key = null;
        String rateLimitType = "";
        String interval = "";
        int intervalNum = 0;
        int limit = 0;
        while (parser.hasNext()) {
            JsonParser.Event event = parser.next();
            switch (event) {
                case KEY_NAME -> key = parser.getString();
                case VALUE_STRING -> {
                    if ("rateLimitType".equals(key)) {
                        rateLimitType = parser.getString();
                    }
                    if ("interval".equals(key)) {
                        interval = parser.getString();
                    }
                }
                case VALUE_NUMBER -> {
                    if ("intervalNum".equals(key)) {
                        intervalNum = parser.getInt();
                    }
                    if ("limit".equals(key)) {
                        limit = parser.getInt();
                    }
                }
                default -> {
                }
            }
        }
        return new RateLimitDTO(rateLimitType,
                interval,
                intervalNum,
                limit);
    }
}
