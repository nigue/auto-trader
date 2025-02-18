package com.tapir.goose.data.deserializer;

import com.tapir.goose.data.dto.CommissionRatesDTO;
import jakarta.json.bind.serializer.DeserializationContext;
import jakarta.json.bind.serializer.JsonbDeserializer;
import jakarta.json.stream.JsonParser;

import java.lang.reflect.Type;
import java.math.BigDecimal;

public class CommissionRatesDeserializer implements JsonbDeserializer<CommissionRatesDTO> {

    @Override
    public CommissionRatesDTO deserialize(JsonParser parser,
                                          DeserializationContext ctx,
                                          Type type) {
        String key = null;
        BigDecimal maker = BigDecimal.ZERO;
        BigDecimal taker = BigDecimal.ZERO;
        BigDecimal buyer = BigDecimal.ZERO;
        BigDecimal seller = BigDecimal.ZERO;
        while (parser.hasNext()) {
            JsonParser.Event event = parser.next();
            switch (event) {
                case KEY_NAME -> key = parser.getString();
                case VALUE_STRING -> {
                    if ("maker".equals(key)) {
                        maker = new BigDecimal(parser.getString());
                    }
                    if ("taker".equals(key)) {
                        taker = new BigDecimal(parser.getString());
                    }
                    if ("buyer".equals(key)) {
                        buyer = new BigDecimal(parser.getString());
                    }
                    if ("seller".equals(key)) {
                        seller = new BigDecimal(parser.getString());
                    }
                }
                default -> {
                }
            }
        }
        return new CommissionRatesDTO(maker,
                taker,
                buyer,
                seller);
    }
}
