package com.tapir.goose.data.gateway;

import com.tapir.goose.data.dto.LoginDTO;
import com.tapir.goose.data.dto.MarketOrderAllFreeRequestDTO;
import com.tapir.goose.data.dto.OrderSide;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

class MarketOrderGatewayIntegrationTest {

    private MarketOrderGateway gateway;
    LoginDTO login;
    MarketOrderAllFreeRequestDTO dto;

    @BeforeEach
    void init() {
        String key = System.getenv("BINANCE_KEY");
        String secret = System.getenv("BINANCE_SECRET");
        login = new LoginDTO(key, secret);
        gateway = new MarketOrderGateway();
        dto = new MarketOrderAllFreeRequestDTO("btcusdt",
                OrderSide.BUY);
    }

    @Test
    @DisplayName("Binance order market")
    void test() {
        var response = gateway.order(login, dto);
        assertNotEquals(0, "dto.balances().size()");
    }

}
