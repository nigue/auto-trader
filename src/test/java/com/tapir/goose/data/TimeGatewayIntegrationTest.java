package com.tapir.goose.data;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;


class TimeGatewayIntegrationTest {

    private TimeGateway gateway;

    @BeforeEach
    void init() {
        gateway = new TimeGateway();
    }

    @Test
    @DisplayName("Binance time")
    void test() {
        var fetch = gateway.fetch();
        assertNotNull(fetch);
    }
}