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
import java.util.stream.Collectors;

@ApplicationScoped
public class OptionsService {

    private static final Logger logger = LogManager.getLogger(OptionsService.class);
    private static final BigDecimal HUNDRED = BigDecimal.valueOf(100L);

    private final KlineGateway klineGateway;
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

    @Inject
    public OptionsService(KlineGateway klineGateway) {
        this.klineGateway = klineGateway;
    }

    public List<BinanceVDO> process() {
        return availableSymbols.stream()
                .map(this::analysisSymbol)
                .filter(it -> it.red() > 6)
                .filter(it -> BigDecimal.ZERO.compareTo(it.fall()) > 0)
                .collect(Collectors.toList());
    }

    private BinanceVDO analysisSymbol(String symbol) {
        Interval interval = Interval.FIFTEEN_MINUTES;
        int limit = 10;
        List<KlineDTO> klines = klineGateway.findAll(symbol, interval, limit);
        klines.sort(Comparator.comparing(KlineDTO::closeTime));
        int red = (int) klines.stream().filter(KlineDTO::isRed).count();
        if (!klines.get(limit - 1).isRed()) {
            red = 1;
        }
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
        return new BinanceVDO(symbol,
                interval.name(),
                red,
                fall);
    }
}
