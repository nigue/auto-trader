package com.tapir.goose.data.deserializer;

import com.tapir.goose.data.dto.PriceDTO;
import jakarta.json.bind.serializer.DeserializationContext;
import jakarta.json.bind.serializer.JsonbDeserializer;
import jakarta.json.stream.JsonParser;

import java.lang.reflect.Type;
import java.math.BigDecimal;

public class PriceDeserializer implements JsonbDeserializer<PriceDTO> {

    @Override
    public PriceDTO deserialize(JsonParser parser,
                                DeserializationContext ctx,
                                Type type) {
        String key = null;
        String symbol = "";
        BigDecimal price = BigDecimal.ZERO;
        while (parser.hasNext()) {
            JsonParser.Event event = parser.next();
            switch (event) {
                case KEY_NAME -> key = parser.getString();
                case VALUE_STRING -> {
                    if ("symbol".equals(key)) {
                        symbol = parser.getString();
                    }
                    if ("price".equals(key)) {
                        price = new BigDecimal(parser.getString());
                    }
                }
                default -> {
                }
            }
        }
        return new PriceDTO(symbol,
                price);
    }
}
