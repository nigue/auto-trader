package com.tapir.goose.data;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Invocation;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@ApplicationScoped
public class PingGateway {

    private static final String API_URL = "https://api.binance.com/api/v3/ping";

    public Boolean fetch() {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(API_URL);

        Invocation.Builder request = target.request(MediaType.APPLICATION_JSON);
        Response response = request.get();
        client.close();

        return response.getStatus() == 200;
    }
}
