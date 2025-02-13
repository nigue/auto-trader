package com.tapir.goose.data;

import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


class TimeGatewayIntegrationTest {

    private TimeGateway gateway;

    @BeforeEach
    void init() {
        gateway = new TimeGateway();
    }

    //@Test
    @DisplayName("My 1st JUnit 5 test!")
    void test() {
        String fetch = gateway.fetch();
        assertEquals("Hayden, Josh", fetch);
    }
}