package com.tapir.goose.data.deserializer;

import com.tapir.goose.data.dto.ExchangeInfoDTO;
import jakarta.json.bind.serializer.DeserializationContext;
import jakarta.json.bind.serializer.JsonbDeserializer;
import jakarta.json.stream.JsonParser;

import java.lang.reflect.Type;

public class ExchangeInfoDeserializer implements JsonbDeserializer<ExchangeInfoDTO> {

    @Override
    public ExchangeInfoDTO deserialize(JsonParser parser,
                                       DeserializationContext deserializationContext,
                                       Type type) {
        String key = null;
        String timeZone = "";
        long serverTime = 0L;
        while (parser.hasNext()) {
            JsonParser.Event event = parser.next();
            switch (event) {
                case KEY_NAME -> key = parser.getString();
                case VALUE_STRING -> {
                    if ("timezone".equals(key)) {
                        timeZone = parser.getString();
                    }
                }
                case VALUE_NUMBER -> {
                    if ("serverTime".equals(key)) {
                        serverTime = parser.getLong();
                    }
                }
                default -> {
                }
            }
        }
        return new ExchangeInfoDTO(timeZone,
                serverTime);
    }
}
