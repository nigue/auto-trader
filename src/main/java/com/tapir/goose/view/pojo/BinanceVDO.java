package com.tapir.goose.view.pojo;

import java.math.BigDecimal;

public record BinanceVDO(String symbol,
                         String interval,
                         Integer red,
                         BigDecimal fall) {
    
}
