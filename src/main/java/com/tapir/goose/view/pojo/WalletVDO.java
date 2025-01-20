package com.tapir.goose.view.pojo;

import java.math.BigDecimal;

public record WalletVDO(Boolean free,
                        String symbol,
                        BigDecimal market,
                        BigDecimal exit,
                        BigDecimal now,
                        BigDecimal enter,
                        String date) {
}
