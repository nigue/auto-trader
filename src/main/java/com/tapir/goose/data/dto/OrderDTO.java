package com.tapir.goose.data.dto;

import java.math.BigDecimal;

public record OrderDTO(
        String symbol,
        String clientOrderId,
        BigDecimal price,
        BigDecimal origQty,
        BigDecimal executedQty,
        BigDecimal cummulativeQuoteQty,
        OrderStatus status,
        OrderType type,
        OrderSide side,
        BigDecimal stopPrice,
        Long time,
        Long updateTime
) {
}
