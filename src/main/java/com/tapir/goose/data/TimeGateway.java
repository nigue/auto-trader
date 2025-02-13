package com.tapir.goose.data;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Invocation;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

public class TimeGateway {

    private static final String API_URL = "https://api.binance.com/api/v3/time";

    public String fetch() {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(API_URL);

        Invocation.Builder request = target.request(MediaType.APPLICATION_JSON);
        Response response = request.get();
        String result = response.readEntity(String.class);
        client.close();

        return result;
    }
}
