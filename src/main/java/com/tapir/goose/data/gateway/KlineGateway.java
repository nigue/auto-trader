package com.tapir.goose.data.gateway;

import com.tapir.goose.data.dto.Interval;
import com.tapir.goose.data.dto.KlineDTO;
import com.tapir.goose.data.dto.KlinesDTO;

import java.util.List;
import java.util.Locale;
import java.util.Map;

public class KlineGateway extends BinanceGateway<KlinesDTO> {

    public KlineGateway() {
        super("/klines");
    }

    public List<KlineDTO> findAll(String symbol,
                                  Interval interval,
                                  Integer limit) {
        String value = symbol.toUpperCase(Locale.ROOT);
        Map<String, String> map = Map.of("symbol", value,
                "interval", interval.value,
                "limit", String.valueOf(limit));
        KlinesDTO dataArray = fetch(map);
        return dataArray.klines();
    }
}
