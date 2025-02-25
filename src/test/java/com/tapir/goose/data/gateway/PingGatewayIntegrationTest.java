package com.tapir.goose.data.gateway;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class PingGatewayIntegrationTest {

    private PingGateway gateway;

    @BeforeEach
    void init() {
        gateway = new PingGateway();
    }

    @Test
    @DisplayName("Binance ping")
    void test() {
        var dto = gateway.alive();
        assertNotNull(dto);
    }
}