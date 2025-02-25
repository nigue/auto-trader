package com.tapir.goose.data.gateway;

import com.tapir.goose.data.deserializer.*;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.json.bind.JsonbConfig;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Invocation;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;

public abstract class BinanceGateway<T> {

    protected static final String base = "https://api.binance.com/api/v3";

    private final String url;
    private final Jsonb jsonb;

    public BinanceGateway(String path) {
        this.url = base.concat(path);
        var config = new JsonbConfig().withDeserializers(
                new TimeDeserializer(),
                new PingDeserializer(),
                new ExchangeInfoDeserializer(),
                new PriceDeserializer(),
                new KlinesDeserializer());
        this.jsonb = JsonbBuilder.create(config);
    }

    T fetch() {
        return fetch(Map.of());
    }

    T fetch(Map<String, String> map) {
        String params = map.entrySet().stream()
                .map(entry -> entry.getKey() + "=" + entry.getValue())
                .reduce((left, right) -> left + "&" + right)
                .map(result -> "?" + result)
                .orElse("");

        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(url.concat(params));

        Invocation.Builder request = target.request(MediaType.APPLICATION_JSON);
        Response response = request.get();
        String json = response.readEntity(String.class);
        T result = jsonb.fromJson(json, getGenericType());

        client.close();
        return result;
    }

    @SuppressWarnings("unchecked")
    private Class<T> getGenericType() {
        Type superClass = getClass().getGenericSuperclass();
        if (superClass instanceof ParameterizedType parameterizedType) {
            Type type = parameterizedType.getActualTypeArguments()[0];
            if (type instanceof Class<?>) return (Class<T>) type;
        }
        throw new RuntimeException("No se pudo determinar el tipo genérico.");
    }

}
