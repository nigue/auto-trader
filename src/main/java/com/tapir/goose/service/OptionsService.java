package com.tapir.goose.service;

import com.tapir.goose.data.dto.Interval;
import com.tapir.goose.data.dto.KlineDTO;
import com.tapir.goose.data.gateway.KlineGateway;
import com.tapir.goose.view.pojo.BinanceVDO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@ApplicationScoped
public class OptionsService {

    private static final Logger logger = LogManager.getLogger(OptionsService.class);
    private static final BigDecimal HUNDRED = BigDecimal.valueOf(100L);

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
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    private BinanceVDO analysisSymbol(String symbol) {
        Interval interval = Interval.FIFTEEN_MINUTES;
        int limit = 10;
        List<KlineDTO> klines = klineGateway.findAll(symbol, interval, limit);
        klines.sort(Comparator.comparing(KlineDTO::closeTime));
        int red = (int) klines.stream().filter(KlineDTO::isRed).count();
        BigDecimal firstPrice = klines.get(0).open();
        BigDecimal lastPrice = klines.get(limit - 1).close();
        BigDecimal fall = HUNDRED.multiply(lastPrice)
                .divide(firstPrice, RoundingMode.DOWN)
                .subtract(HUNDRED);
        logger.info("symbol: {}", symbol);
        logger.info("red: {}", red);
        logger.info("firstPrice: {}", firstPrice);
        logger.info("lastPrice: {}", lastPrice);
        logger.info("fall: {}", fall);
        if (!okConditions(symbol, limit, klines, red, fall)) {
            return null;
        }
        return new BinanceVDO(symbol,
                interval.name(),
                red,
                fall);
    }

    private boolean okConditions(String symbol,
                                 int limit,
                                 List<KlineDTO> klines,
                                 int red,
                                 BigDecimal fall) {
        boolean last1Red = klines.get(limit - 1).isRed();
        boolean last2Red = klines.get(limit - 2).isRed();
        boolean last3Red = klines.get(limit - 3).isRed();
        boolean totalOk = 6 < red;
        boolean isNegative = BigDecimal.ZERO.compareTo(fall) > 0;

        return last1Red
                && last2Red
                && last3Red
                && totalOk
                && isNegative;
    }
}
