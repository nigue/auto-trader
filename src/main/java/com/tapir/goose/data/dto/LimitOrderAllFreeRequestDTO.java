package com.tapir.goose.data.dto;

import java.math.BigDecimal;

public record LimitOrderAllFreeRequestDTO(
        String symbol,
        OrderSide side,
        BigDecimal price
) {
}
