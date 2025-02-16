package com.tapir.goose.data.deserializer;

import com.tapir.goose.data.dto.RateLimitDTO;
import com.tapir.goose.data.dto.SymbolDTO;
import jakarta.json.bind.serializer.DeserializationContext;
import jakarta.json.bind.serializer.JsonbDeserializer;
import jakarta.json.stream.JsonParser;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class SymbolDeserializer implements JsonbDeserializer<SymbolDTO> {

    @Override
    public SymbolDTO deserialize(JsonParser parser,
                                 DeserializationContext ctx,
                                 Type type) {
        String key = null;
        String symbol = "";
        String status = "";
        String baseAsset = "";
        int baseAssetPrecision = 0;
        String quoteAsset = "";
        int quotePrecision = 0;
        int quoteAssetPrecision = 0;
        int baseCommissionPrecision = 0;
        int quoteCommissionPrecision = 0;
        List<String> orderTypes = new ArrayList<>();
        Boolean icebergAllowed = null;
        while (parser.hasNext()) {
            JsonParser.Event event = parser.next();
            switch (event) {
                case KEY_NAME -> key = parser.getString();
                case VALUE_STRING -> {
                    if ("symbol".equals(key)) {
                        symbol = parser.getString();
                    }
                    if ("status".equals(key)) {
                        status = parser.getString();
                    }
                    if ("baseAsset".equals(key)) {
                        baseAsset = parser.getString();
                    }
                    if ("quoteAsset".equals(key)) {
                        quoteAsset = parser.getString();
                    }
                }
                case VALUE_NUMBER -> {
                    if ("baseAssetPrecision".equals(key)) {
                        baseAssetPrecision = parser.getInt();
                    }
                    if ("quotePrecision".equals(key)) {
                        quotePrecision = parser.getInt();
                    }
                    if ("quoteAssetPrecision".equals(key)) {
                        quoteAssetPrecision = parser.getInt();
                    }
                    if ("baseCommissionPrecision".equals(key)) {
                        quoteAssetPrecision = parser.getInt();
                    }
                    if ("quoteCommissionPrecision".equals(key)) {
                        quoteAssetPrecision = parser.getInt();
                    }
                }
                case VALUE_FALSE, VALUE_TRUE -> {
                    if ("icebergAllowed".equals(key)) {
                        icebergAllowed = parser.getString().equals("true");
                    }
                }
                case START_ARRAY -> {
                    if ("orderTypes".equals(key)) {
                        while (parser.hasNext() && parser.next() != JsonParser.Event.END_ARRAY) {
                            orderTypes.add(ctx.deserialize(String.class, parser));
                        }
                    }
                }
                default -> {
                }
            }
        }
        return new SymbolDTO(symbol,
                status,
                baseAsset,
                baseAssetPrecision,
                quoteAsset,
                quotePrecision,
                quoteAssetPrecision,
                baseCommissionPrecision,
                quoteCommissionPrecision,
                orderTypes,
                icebergAllowed);
    }
}
