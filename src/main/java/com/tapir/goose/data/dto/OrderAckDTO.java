package com.tapir.goose.data.dto;

public record OrderAckDTO(
        String symbol,
        Integer orderId,
        Integer orderListId,
        String clientOrderId,
        Long transactTime
) {
}
