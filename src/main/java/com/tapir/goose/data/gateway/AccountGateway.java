package com.tapir.goose.data.gateway;

import com.tapir.goose.data.deserializer.AccountDeserializer;
import com.tapir.goose.data.dto.AccountDTO;
import com.tapir.goose.data.dto.LoginDTO;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryConfig;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.json.bind.JsonbConfig;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Invocation;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

@ApplicationScoped
public class AccountGateway {

    private static final Logger logger = LogManager.getLogger(AccountGateway.class);

    private final List<String> BASE_URLS = List.of(
            "https://api.binance.com/api/v3/account",
            "https://api1.binance.com/api/v3/account",
            "https://api2.binance.com/api/v3/account",
            "https://api3.binance.com/api/v3/account"
    );

    private final Jsonb jsonb;
    private final TimeGateway timeGateway;

    private final Retry retry;
    private int attempt = 0;

    public AccountGateway() {
        var config = new JsonbConfig().withDeserializers(
                new AccountDeserializer());
        this.jsonb = JsonbBuilder.create(config);
        this.timeGateway = new TimeGateway();

        RetryConfig retryConfig = RetryConfig.custom()
                .maxAttempts(3)
                .waitDuration(Duration.ofSeconds(2))
                .retryOnResult(it -> {
                    logger.info("attempt: {}", attempt);
                    attempt++;
                    Response response = (Response) it;
                    return response.getStatus() != 200;
                })
                .retryExceptions(Exception.class)
                .build();
        this.retry = Retry.of("account_retry", retryConfig);
    }

    public AccountDTO get(LoginDTO login) {
        attempt = 0;
        Supplier<Response> responseSupplier =
                Retry.decorateSupplier(retry, () -> fetch(login));

        try (Response response = responseSupplier.get()) {
            logger.info("status: {}", response.getStatus());
            if (response.getStatus() == 200) {
                String json = response.readEntity(String.class);
                return jsonb.fromJson(json, AccountDTO.class);
            } else {
                throw new RuntimeException("Error en la respuesta: " + response.getStatus());
            }
        }
    }

    private Response fetch(LoginDTO login) {
        Map<String, String> map = new HashMap<>(Map.of(
                "omitZeroBalances", "true",
                "recvWindow", Long.toString(2500L),
                "timestamp", timeGateway.get().serverTime().toString()));

        map.put("signature", signature(map, login.secret()));

        String params = map.entrySet().stream()
                .map(entry -> entry.getKey() + "=" + entry.getValue())
                .reduce((left, right) -> left + "&" + right)
                .map(result -> "?" + result)
                .orElse("");

        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(BASE_URLS.get(attempt).concat(params));

        Invocation.Builder request = target.request(MediaType.APPLICATION_JSON)
                .header("X-MBX-APIKEY", login.key());
        Response response = request.get();
        client.close();
        return response;
    }

    private String signature(Map<String, String> map, String secret) {
        String params = map.entrySet().stream()
                .map(entry -> entry.getKey() + "=" + entry.getValue())
                .reduce((left, right) -> left + "&" + right)
                .orElse("");
        SslComponent ssl = new SslComponent();
        return ssl.sign(params, secret);
    }
}
