package com.tapir.goose.view.pojo;

import java.math.BigDecimal;

public record UserVDO (String username,
                       String market,
                       BigDecimal sell,
                       BigDecimal objective,
                       BigDecimal actual) {


}