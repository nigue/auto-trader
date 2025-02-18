package com.tapir.goose.data.dto;

import java.math.BigDecimal;

public record MarketOrderRequestDTO(
        String symbol,
        OrderSide side,
        BigDecimal quoteOrderQty
) {
}
