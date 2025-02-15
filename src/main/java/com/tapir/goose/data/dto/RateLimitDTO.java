package com.tapir.goose.data.dto;

public record RateLimitDTO(
        String rateLimitType,
        String interval,
        Integer intervalNum,
        Integer limit
) {
}
