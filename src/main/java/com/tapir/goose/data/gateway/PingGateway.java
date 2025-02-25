package com.tapir.goose.data.gateway;

import com.tapir.goose.data.dto.PingDTO;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PingGateway extends BinanceGateway<PingDTO> {

    public PingGateway() {
        super("/ping");
    }

    public PingDTO alive() {
        return super.fetch();
    }
}
