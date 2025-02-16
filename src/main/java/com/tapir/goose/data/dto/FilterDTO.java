package com.tapir.goose.data.dto;

import java.math.BigDecimal;

public record FilterDTO(
        String filterType,
        BigDecimal minPrice,
        BigDecimal maxPrice,
        BigDecimal tickSize
) {
}
