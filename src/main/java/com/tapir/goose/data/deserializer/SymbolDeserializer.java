package com.tapir.goose.data.deserializer;

import com.tapir.goose.data.dto.FilterDTO;
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
        Boolean ocoAllowed = null;
        Boolean otoAllowed = null;
        Boolean quoteOrderQtyMarketAllowed = null;
        Boolean allowTrailingStop = null;
        Boolean cancelReplaceAllowed = null;
        Boolean isSpotTradingAllowed = null;
        Boolean isMarginTradingAllowed = null;
        List<FilterDTO> filters = new ArrayList<>();
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
                    if ("ocoAllowed".equals(key)) {
                        ocoAllowed = parser.getString().equals("true");
                    }
                    if ("otoAllowed".equals(key)) {
                        otoAllowed = parser.getString().equals("true");
                    }
                    if ("quoteOrderQtyMarketAllowed".equals(key)) {
                        quoteOrderQtyMarketAllowed = parser.getString().equals("true");
                    }
                    if ("allowTrailingStop".equals(key)) {
                        allowTrailingStop = parser.getString().equals("true");
                    }
                    if ("cancelReplaceAllowed".equals(key)) {
                        cancelReplaceAllowed = parser.getString().equals("true");
                    }
                    if ("isSpotTradingAllowed".equals(key)) {
                        isSpotTradingAllowed = parser.getString().equals("true");
                    }
                    if ("isMarginTradingAllowed".equals(key)) {
                        isMarginTradingAllowed = parser.getString().equals("true");
                    }
                }
                case START_ARRAY -> {
                    if ("orderTypes".equals(key)) {
                        while (parser.hasNext() && parser.next() != JsonParser.Event.END_ARRAY) {
                            orderTypes.add(ctx.deserialize(String.class, parser));
                        }
                    }
                    if ("filters".equals(key)) {
                        while (parser.hasNext() && parser.next() != JsonParser.Event.END_ARRAY) {
                            filters.add(ctx.deserialize(FilterDTO.class, parser));
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
                icebergAllowed,
                ocoAllowed,
                otoAllowed,
                quoteOrderQtyMarketAllowed,
                allowTrailingStop,
                cancelReplaceAllowed,
                isSpotTradingAllowed,
                isMarginTradingAllowed,
                filters);
    }
}
