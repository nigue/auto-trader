package com.tapir.goose.service;

import com.tapir.goose.data.gateway.KlineGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

class OptionsServiceTest {

    private OptionsService gateway;

    @BeforeEach
    void init() {
        gateway = new OptionsService();
    }

    //@Test
    @DisplayName("Service options")
    void test() {
        var dto = gateway.process();
        assertNotEquals(0, dto.size());
    }
}