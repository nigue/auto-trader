package com.tapir.goose.data.gateway;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotEquals;


class TimeGatewayIntegrationTest {

    private TimeGateway gateway;

    @BeforeEach
    void init() {
        gateway = new TimeGateway();
    }

    @Test
    @DisplayName("Binance time")
    void test() {
        var dto = gateway.get();
        assertNotEquals(0L, dto.serverTime());
    }
}