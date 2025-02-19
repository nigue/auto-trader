package com.tapir.goose.data.deserializer;

import com.tapir.goose.data.dto.*;
import jakarta.json.bind.serializer.DeserializationContext;
import jakarta.json.bind.serializer.JsonbDeserializer;
import jakarta.json.stream.JsonParser;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class OrdersDeserializer implements JsonbDeserializer<OrdersDTO> {

    @Override
    public OrdersDTO deserialize(JsonParser parser,
                                 DeserializationContext ctx,
                                 Type type) {
        List<OrderDTO> orders = new ArrayList<>();
        while (parser.hasNext() && parser.next() != JsonParser.Event.END_ARRAY) {
            orders.add(ctx.deserialize(OrderDTO.class, parser));
        }
        return new OrdersDTO(orders);
    }
}
