package com.tapir.goose.data.gateway;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

class PriceGatewayIntegrationTest {

    private PriceGateway gateway;

    @BeforeEach
    void init() {
        gateway = new PriceGateway();
    }

    @Test
    @DisplayName("Binance price")
    void test() {
        var dto = gateway.get("btcusdt");
        assertNotEquals("", dto.symbol());
        assertNotEquals(BigDecimal.ZERO, dto.price());
    }
}