package com.tapir.goose.data.deserializer;

import com.tapir.goose.data.dto.ExchangeInfoDTO;
import com.tapir.goose.data.dto.RateLimitDTO;
import com.tapir.goose.data.dto.SymbolDTO;
import jakarta.json.bind.serializer.DeserializationContext;
import jakarta.json.bind.serializer.JsonbDeserializer;
import jakarta.json.stream.JsonParser;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ExchangeInfoDeserializer implements JsonbDeserializer<ExchangeInfoDTO> {

    @Override
    public ExchangeInfoDTO deserialize(JsonParser parser,
                                       DeserializationContext ctx,
                                       Type type) {
        String key = null;
        String timeZone = "";
        long serverTime = 0L;
        List<RateLimitDTO> rateLimits = new ArrayList<>();
        List<SymbolDTO> symbols = new ArrayList<>();
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
                case START_ARRAY -> {
                    if ("rateLimits".equals(key)) {
                        while (parser.hasNext() && parser.next() != JsonParser.Event.END_ARRAY) {
                            rateLimits.add(ctx.deserialize(RateLimitDTO.class, parser));
                        }
                    }
                    if ("symbols".equals(key)) {
                        while (parser.hasNext() && parser.next() != JsonParser.Event.END_ARRAY) {
                            symbols.add(ctx.deserialize(SymbolDTO.class, parser));
                        }
                    }
                }
                default -> {
                }
            }
        }
        return new ExchangeInfoDTO(timeZone,
                serverTime,
                rateLimits,
                symbols);
    }
}
