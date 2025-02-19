package com.tapir.goose.data.gateway;

import com.tapir.goose.data.dto.LimitOrderAllFreeRequestDTO;
import com.tapir.goose.data.dto.LoginDTO;
import com.tapir.goose.data.dto.MarketOrderAllFreeRequestDTO;
import com.tapir.goose.data.dto.OrderSide;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

class LimitOrderGatewayIntegrationTest {

    private LimitOrderGateway gateway;
    LoginDTO login;
    LimitOrderAllFreeRequestDTO dto;

    @BeforeEach
    void init() {
        String key = System.getenv("BINANCE_KEY");
        String secret = System.getenv("BINANCE_SECRET");
        login = new LoginDTO(key, secret);
        gateway = new LimitOrderGateway();
        dto = new LimitOrderAllFreeRequestDTO("btcusdt",
                OrderSide.BUY,
                BigDecimal.valueOf(50000L));
    }

    //@Test
    @DisplayName("Binance order limit")
    void test() {
        var response = gateway.order(login, dto);
        assertNotEquals(0, "dto.balances().size()");
    }

}
