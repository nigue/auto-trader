package com.tapir.goose.service;

import com.tapir.goose.data.dto.Interval;
import com.tapir.goose.data.dto.KlineDTO;
import com.tapir.goose.data.gateway.KlineGateway;
import com.tapir.goose.view.pojo.BinanceVDO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class OptionsService {

    @Inject
    private KlineGateway klineGateway;
    private List<String> availableSymbols = Arrays.asList(
            "BTCUSDT",
            "ETHUSDT",
            "BNBUSDT",
            "LTCUSDT",
            "SOLUSDT",
            "ADAUSDT",
            "DASHUSDT",
            "IOTAUSDT"
    );

    public List<BinanceVDO> process() {
        return availableSymbols.stream()
                .map(this::analysisSymbol)
                .filter(it -> it.red() > 3)
                .filter(it -> BigDecimal.ZERO.compareTo(it.fall()) > 0)
                .collect(Collectors.toList());
    }

    private BinanceVDO analysisSymbol(String symbol) {
        Interval interval = Interval.FIFTEEN_MINUTES;
        int limit = 5;
        List<KlineDTO> klines = klineGateway.findAll(symbol, interval, limit);
        klines.sort(Comparator.comparing(KlineDTO::closeTime));
        Integer red = 0;
        for (KlineDTO kline : klines) {
            if (kline.isRed()) {
                red++;
            } else {
                break;
            }
        }
        BigDecimal fall = klines.get(0).open().subtract(klines.get(limit - 1).close());
        return new BinanceVDO(symbol,
                interval.name(),
                red,
                fall);
    }
}
