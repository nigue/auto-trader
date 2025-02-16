package com.tapir.goose.data.gateway;

import com.tapir.goose.data.dto.TimeDTO;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class TimeGateway extends BinanceGateway<TimeDTO> {

    public TimeGateway() {
        super("/time");
    }

    public TimeDTO get() {
        return super.fetch();
    }
}
