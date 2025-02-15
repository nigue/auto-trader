package com.tapir.goose.data.gateway;

import com.tapir.goose.data.dto.ExchangeInfoDTO;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Locale;
import java.util.Map;

@ApplicationScoped
public class ExchangeInfoGateway extends BinanceGateway<ExchangeInfoDTO> {

    public ExchangeInfoGateway() {
        super("/exchangeInfo");
    }

    public ExchangeInfoDTO fetch(String symbol) {
        String value = symbol.toUpperCase(Locale.ROOT);
        Map<String, String> map = Map.of("symbol", value,
                "showPermissionSets", "false");
        return super.fetch(map);
    }
}
