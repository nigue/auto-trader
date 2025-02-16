package com.tapir.goose.data.gateway;

import com.tapir.goose.data.dto.PriceDTO;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Locale;
import java.util.Map;

@ApplicationScoped
public class PriceGateway extends BinanceGateway<PriceDTO> {

    public PriceGateway() {
        super("/ticker/price");
    }

    public PriceDTO fetch(String symbol) {
        String value = symbol.toUpperCase(Locale.ROOT);
        Map<String, String> map = Map.of("symbol", value);
        return super.fetch(map);
    }
}
