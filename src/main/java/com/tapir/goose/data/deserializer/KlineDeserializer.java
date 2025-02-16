package com.tapir.goose.data.deserializer;

import com.tapir.goose.data.dto.KlineDTO;
import jakarta.json.bind.serializer.DeserializationContext;
import jakarta.json.bind.serializer.JsonbDeserializer;
import jakarta.json.stream.JsonParser;

import java.lang.reflect.Type;
import java.math.BigDecimal;

public class KlineDeserializer implements JsonbDeserializer<KlineDTO> {

    @Override
    public KlineDTO deserialize(JsonParser parser,
                                DeserializationContext ctx,
                                Type type) {
        Long openTime = 0L;
        BigDecimal open = BigDecimal.ZERO;
        BigDecimal high = BigDecimal.ZERO;
        BigDecimal low = BigDecimal.ZERO;
        BigDecimal close = BigDecimal.ZERO;
        BigDecimal volume = BigDecimal.ZERO;
        Long closeTime = 0L;
        BigDecimal assetVolume = BigDecimal.ZERO;
        Integer trades = 0;


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
        return new KlineDTO(openTime,
                open,
                high,
                low,
                close,
                volume,
                closeTime,
                assetVolume,
                trades);
    }
}
