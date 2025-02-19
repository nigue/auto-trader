package com.tapir.goose.data.dto;

import java.util.List;

public record OrdersDTO(
        List<OrderDTO> orders
) {
}
