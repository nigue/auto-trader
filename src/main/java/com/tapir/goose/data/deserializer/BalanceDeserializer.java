package com.tapir.goose.data.deserializer;

import com.tapir.goose.data.dto.BalanceDTO;
import jakarta.json.bind.serializer.DeserializationContext;
import jakarta.json.bind.serializer.JsonbDeserializer;
import jakarta.json.stream.JsonParser;

import java.lang.reflect.Type;
import java.math.BigDecimal;

public class BalanceDeserializer implements JsonbDeserializer<BalanceDTO> {

    @Override
    public BalanceDTO deserialize(JsonParser parser,
                                  DeserializationContext ctx,
                                  Type type) {
        String key = null;
        String asset = "";
        BigDecimal free = BigDecimal.ZERO;
        BigDecimal locked = BigDecimal.ZERO;
        while (parser.hasNext()) {
            JsonParser.Event event = parser.next();
            switch (event) {
                case KEY_NAME -> key = parser.getString();
                case VALUE_STRING -> {
                    if ("asset".equals(key)) {
                        asset = parser.getString();
                    }
                    if ("free".equals(key)) {
                        free = new BigDecimal(parser.getString());
                    }
                    if ("locked".equals(key)) {
                        locked = new BigDecimal(parser.getString());
                    }
                }
                default -> {
                }
            }
        }
        return new BalanceDTO(asset,
                free,
                locked);
    }
}
