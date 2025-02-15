package com.tapir.goose.data.gateway;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ExchangeInfoGatewayIntegrationTest {

    private ExchangeInfoGateway gateway;

    @BeforeEach
    void init() {
        gateway = new ExchangeInfoGateway();
    }

    @Test
    @DisplayName("Binance exchange info")
    void test() {
        var dto = gateway.fetch("btcusdt");
        assertNotEquals("", dto.timeZone());
        assertNotEquals(0L, dto.serverTime());
    }
}