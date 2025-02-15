package com.tapir.goose.data.dto;

import java.util.List;

public record ExchangeInfoDTO(
        String timeZone,
        Long serverTime,
        List<RateLimitDTO> rateLimits
) {
}
