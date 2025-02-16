package com.tapir.goose.data.deserializer;

import com.tapir.goose.data.dto.KlineDTO;
import com.tapir.goose.data.dto.KlinesDTO;
import jakarta.json.bind.serializer.DeserializationContext;
import jakarta.json.bind.serializer.JsonbDeserializer;
import jakarta.json.stream.JsonParser;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class KlinesDeserializer implements JsonbDeserializer<KlinesDTO> {

    @Override
    public KlinesDTO deserialize(JsonParser parser,
                                 DeserializationContext ctx,
                                 Type type) {
        List<KlineDTO> klines = new ArrayList<>();
        while (parser.hasNext()) {
            JsonParser.Event event = parser.next();
            switch (event) {
                case START_ARRAY -> {
                    while (parser.hasNext() && parser.next() != JsonParser.Event.END_ARRAY) {
                        klines.add(kline(parser));
                    }
                }
                default -> {
                }
            }
        }
        return new KlinesDTO(klines);
    }

    private KlineDTO kline(JsonParser parser) {

        Long openTime = parser.getLong();
        parser.next();
        BigDecimal open = new BigDecimal(parser.getString());
        parser.next();
        BigDecimal high = new BigDecimal(parser.getString());
        parser.next();
        BigDecimal low = new BigDecimal(parser.getString());
        parser.next();
        BigDecimal close = new BigDecimal(parser.getString());
        parser.next();
        BigDecimal volume = new BigDecimal(parser.getString());
        parser.next();
        Long closeTime = parser.getLong();
        parser.next();
        BigDecimal assetVolume = new BigDecimal(parser.getString());
        parser.next();
        Integer trades = parser.getInt();
        parser.next();
        parser.next();
        parser.next();

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
