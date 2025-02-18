package com.tapir.goose.data.dto;

import java.math.BigDecimal;

public record CommissionRatesDTO(
        BigDecimal maker,
        BigDecimal taker,
        BigDecimal buyer,
        BigDecimal seller
) {
}
