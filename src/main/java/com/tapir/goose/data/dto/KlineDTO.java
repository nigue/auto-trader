package com.tapir.goose.data.dto;

import java.math.BigDecimal;

public record KlineDTO(
        Long openTime,
        BigDecimal open,
        BigDecimal high,
        BigDecimal low,
        BigDecimal close,
        BigDecimal volume,
        Long closeTime,
        BigDecimal assetVolume,
        Integer trades
) {

    public boolean isRed() {
        return open.compareTo(close) > 0;
    }
}
