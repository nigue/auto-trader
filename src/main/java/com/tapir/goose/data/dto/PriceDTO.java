package com.tapir.goose.data.dto;

import java.math.BigDecimal;

public record PriceDTO(
        String symbol,
        BigDecimal price
) {
}
