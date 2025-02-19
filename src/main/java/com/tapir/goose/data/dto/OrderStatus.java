package com.tapir.goose.data.dto;

public enum OrderStatus {
    NEW,
    PENDING_NEW,
    PARTIALLY_FILLED,
    FILLED,
    CANCELED,
    PENDING_CANCEL,
    REJECTED,
    EXPIRED,
    EXPIRED_IN_MATCH
}
