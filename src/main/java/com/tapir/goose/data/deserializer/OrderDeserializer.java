package com.tapir.goose.data.deserializer;

import com.tapir.goose.data.dto.OrderDTO;
import com.tapir.goose.data.dto.OrderSide;
import com.tapir.goose.data.dto.OrderStatus;
import com.tapir.goose.data.dto.OrderType;
import jakarta.json.bind.serializer.DeserializationContext;
import jakarta.json.bind.serializer.JsonbDeserializer;
import jakarta.json.stream.JsonParser;

import java.lang.reflect.Type;
import java.math.BigDecimal;

public class OrderDeserializer implements JsonbDeserializer<OrderDTO> {

    @Override
    public OrderDTO deserialize(JsonParser parser,
                                DeserializationContext ctx,
                                Type typeClass) {
        String key = null;
        String symbol = "";
        String clientOrderId = "";
        BigDecimal price = BigDecimal.ZERO;
        BigDecimal origQty = BigDecimal.ZERO;
        BigDecimal executedQty = BigDecimal.ZERO;
        BigDecimal cummulativeQuoteQty = BigDecimal.ZERO;
        OrderStatus status = null;
        OrderType type = null;
        OrderSide side = null;
        BigDecimal stopPrice = BigDecimal.ZERO;
        long time = 0L;
        long updateTime = 0L;
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
                    if ("price".equals(key)) {
                        price = new BigDecimal(parser.getString());
                    }
                    if ("origQty".equals(key)) {
                        origQty = new BigDecimal(parser.getString());
                    }
                    if ("executedQty".equals(key)) {
                        executedQty = new BigDecimal(parser.getString());
                    }
                    if ("cummulativeQuoteQty".equals(key)) {
                        cummulativeQuoteQty = new BigDecimal(parser.getString());
                    }
                    if ("status".equals(key)) {
                        status = OrderStatus.valueOf(parser.getString());
                    }
                    if ("type".equals(key)) {
                        type = OrderType.valueOf(parser.getString());
                    }
                    if ("side".equals(key)) {
                        side = OrderSide.valueOf(parser.getString());
                    }
                    if ("stopPrice".equals(key)) {
                        stopPrice = new BigDecimal(parser.getString());
                    }
                }
                case VALUE_NUMBER -> {
                    if ("time".equals(key)) {
                        time = parser.getLong();
                    }
                    if ("updateTime".equals(key)) {
                        updateTime = parser.getLong();
                    }
                }
                default -> {
                }
            }
        }
        return new OrderDTO(symbol,
                clientOrderId,
                price,
                origQty,
                executedQty,
                cummulativeQuoteQty,
                status,
                type,
                side,
                stopPrice,
                time,
                updateTime);
    }
}
