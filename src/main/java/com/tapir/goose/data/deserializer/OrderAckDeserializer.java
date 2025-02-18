package com.tapir.goose.data.deserializer;

import com.tapir.goose.data.dto.OrderAckDTO;
import jakarta.json.bind.serializer.DeserializationContext;
import jakarta.json.bind.serializer.JsonbDeserializer;
import jakarta.json.stream.JsonParser;

import java.lang.reflect.Type;

public class OrderAckDeserializer implements JsonbDeserializer<OrderAckDTO> {

    @Override
    public OrderAckDTO deserialize(JsonParser parser,
                                   DeserializationContext ctx,
                                   Type type) {
        String key = null;
        String symbol = "";
        int orderId = 0;
        int orderListId = 0;
        String clientOrderId = "";
        long transactTime = 0L;
        while (parser.hasNext()) {
            JsonParser.Event event = parser.next();
            switch (event) {
                case KEY_NAME -> key = parser.getString();
                case VALUE_STRING -> {
                    if ("symbol".equals(key)) {
                        symbol = parser.getString();
                    }
                    if ("clientOrderId".equals(key)) {
                        clientOrderId = parser.getString();
                    }
                }
                case VALUE_NUMBER -> {
                    if ("orderId".equals(key)) {
                        orderId = parser.getInt();
                    }
                    if ("orderListId".equals(key)) {
                        orderListId = parser.getInt();
                    }
                    if ("transactTime".equals(key)) {
                        transactTime = parser.getLong();
                    }
                }
                default -> {
                }
            }
        }
        return new OrderAckDTO(symbol,
                orderId,
                orderListId,
                clientOrderId,
                transactTime);
    }
}
