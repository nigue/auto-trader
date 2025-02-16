package com.tapir.goose.data.deserializer;

import com.tapir.goose.data.dto.FilterDTO;
import jakarta.json.bind.serializer.DeserializationContext;
import jakarta.json.bind.serializer.JsonbDeserializer;
import jakarta.json.stream.JsonParser;

import java.lang.reflect.Type;
import java.math.BigDecimal;

public class FilterDeserializer implements JsonbDeserializer<FilterDTO> {

    @Override
    public FilterDTO deserialize(JsonParser parser,
                                 DeserializationContext ctx,
                                 Type type) {
        String key = null;
        String filterType = "";
        BigDecimal minPrice = BigDecimal.ZERO;
        BigDecimal maxPrice = BigDecimal.ZERO;
        BigDecimal tickSize = BigDecimal.ZERO;
        while (parser.hasNext()) {
            JsonParser.Event event = parser.next();
            switch (event) {
                case KEY_NAME -> key = parser.getString();
                case VALUE_STRING -> {
                    if ("filterType".equals(key)) {
                        filterType = parser.getString();
                    }
                    if ("minPrice".equals(key)) {
                        minPrice = new BigDecimal(parser.getString());
                    }
                    if ("maxPrice".equals(key)) {
                        maxPrice = new BigDecimal(parser.getString());
                    }
                    if ("tickSize".equals(key)) {
                        tickSize = new BigDecimal(parser.getString());
                    }
                }
                default -> {
                }
            }
        }
        return new FilterDTO(filterType,
                minPrice,
                maxPrice,
                tickSize);
    }
}
