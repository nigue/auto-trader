package com.tapir.goose.data.gateway;

import com.tapir.goose.data.dto.Interval;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class KlineGatewayIntegrationTest {

    private KlineGateway gateway;

    @BeforeEach
    void init() {
        gateway = new KlineGateway();
    }

    @Test
    @DisplayName("Binance kilnes")
    void test() {
        var dto = gateway.findAll("btcusdt", Interval.FIVE_MINUTES, 2);
        assertEquals(2, dto.size());
    }
}