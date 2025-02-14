package com.tapir.goose.data;

import com.tapir.goose.data.dto.TimeDTO;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class TimeGateway extends BinanceGateway<TimeDTO> {

    public TimeGateway() {
        super("/time");
    }
}
