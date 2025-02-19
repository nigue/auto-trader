package com.tapir.goose.data.dto;

public record MarketOrderAllFreeRequestDTO(
        String symbol,
        OrderSide side
) {
}
