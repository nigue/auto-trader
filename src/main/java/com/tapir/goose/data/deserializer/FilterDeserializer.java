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
        BigDecimal minQty = BigDecimal.ZERO;
        BigDecimal maxQty = BigDecimal.ZERO;
        BigDecimal stepSize = BigDecimal.ZERO;
        Integer limit = 0;
        Integer minTrailingAboveDelta = 0;
        Integer maxTrailingAboveDelta = 0;
        Integer minTrailingBelowDelta = 0;
        Integer maxTrailingBelowDelta = 0;
        String bidMultiplierUp = "";
        String bidMultiplierDown = "";
        String askMultiplierUp = "";
        String askMultiplierDown = "";
        Integer avgPriceMins = 0;
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
                    if ("minQty".equals(key)) {
                        minQty = new BigDecimal(parser.getString());
                    }
                    if ("maxQty".equals(key)) {
                        maxQty = new BigDecimal(parser.getString());
                    }
                    if ("stepSize".equals(key)) {
                        stepSize = new BigDecimal(parser.getString());
                    }
                    if ("bidMultiplierUp".equals(key)) {
                        bidMultiplierUp = parser.getString();
                    }
                    if ("bidMultiplierDown".equals(key)) {
                        bidMultiplierDown = parser.getString();
                    }
                    if ("askMultiplierUp".equals(key)) {
                        askMultiplierUp = parser.getString();
                    }
                    if ("askMultiplierDown".equals(key)) {
                        askMultiplierDown = parser.getString();
                    }
                }
                case VALUE_NUMBER -> {
                    if ("limit".equals(key)) {
                        limit = parser.getInt();
                    }
                    if ("minTrailingAboveDelta".equals(key)) {
                        minTrailingAboveDelta = parser.getInt();
                    }
                    if ("maxTrailingAboveDelta".equals(key)) {
                        maxTrailingAboveDelta = parser.getInt();
                    }
                    if ("minTrailingBelowDelta".equals(key)) {
                        minTrailingBelowDelta = parser.getInt();
                    }
                    if ("maxTrailingBelowDelta".equals(key)) {
                        maxTrailingBelowDelta = parser.getInt();
                    }
                    if ("avgPriceMins".equals(key)) {
                        avgPriceMins = parser.getInt();
                    }
                }
                default -> {
                }
            }
        }
        return new FilterDTO(filterType,
                minPrice,
                maxPrice,
                tickSize,
                minQty,
                maxQty,
                stepSize,
                limit,
                minTrailingAboveDelta,
                maxTrailingAboveDelta,
                minTrailingBelowDelta,
                maxTrailingBelowDelta,
                bidMultiplierUp,
                bidMultiplierDown,
                askMultiplierUp,
                askMultiplierDown,
                avgPriceMins);
    }
}
