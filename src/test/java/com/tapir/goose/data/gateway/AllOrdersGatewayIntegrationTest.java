package com.tapir.goose.data.gateway;

import com.tapir.goose.data.dto.LoginDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

class AllOrdersGatewayIntegrationTest {

    private AllOrdersGateway gateway;
    private LoginDTO login;

    @BeforeEach
    void init() {
        String key = System.getenv("BINANCE_KEY");
        String secret = System.getenv("BINANCE_SECRET");
        login = new LoginDTO(key, secret);
        gateway = new AllOrdersGateway();
    }

    @Test
    @DisplayName("Binance all orders")
    void test() {
        var dto = gateway.get(login, "btcusdt");
        assertNotEquals(0, dto.size());
    }

}
