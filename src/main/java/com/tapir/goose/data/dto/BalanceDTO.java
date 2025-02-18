package com.tapir.goose.data.dto;

import java.math.BigDecimal;

public record BalanceDTO(
        String asset,
        BigDecimal free,
        BigDecimal locked
) {
}
