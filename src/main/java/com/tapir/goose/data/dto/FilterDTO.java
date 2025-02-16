package com.tapir.goose.data.dto;

import java.math.BigDecimal;

public record FilterDTO(
        String filterType,
        BigDecimal minPrice,
        BigDecimal maxPrice,
        BigDecimal tickSize,
        BigDecimal minQty,
        BigDecimal maxQty,
        BigDecimal stepSize,
        Integer limit,
        Integer minTrailingAboveDelta,
        Integer maxTrailingAboveDelta,
        Integer minTrailingBelowDelta,
        Integer maxTrailingBelowDelta,
        String bidMultiplierUp,
        String bidMultiplierDown,
        String askMultiplierUp,
        String askMultiplierDown,
        Integer avgPriceMins
) {
}
