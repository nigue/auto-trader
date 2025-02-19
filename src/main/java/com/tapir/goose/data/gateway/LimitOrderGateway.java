package com.tapir.goose.data.gateway;

import com.tapir.goose.data.deserializer.OrderAckDeserializer;
import com.tapir.goose.data.dto.*;
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

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.function.Supplier;

@ApplicationScoped
public class LimitOrderGateway {

    private final List<String> BASE_URLS = List.of(
            "https://api.binance.com/api/v3/order",
            "https://api1.binance.com/api/v3/order",
            "https://api2.binance.com/api/v3/order",
            "https://api3.binance.com/api/v3/order"
    );

    private final Jsonb jsonb;
    private final TimeGateway timeGateway;
    private final ExchangeInfoGateway exchangeInfoGateway;
    private final PriceGateway priceGateway;
    private final AccountGateway accountGateway;

    private final Retry retry;
    private int attempt = 0;

    public LimitOrderGateway() {
        var config = new JsonbConfig().withDeserializers(
                new OrderAckDeserializer());
        this.jsonb = JsonbBuilder.create(config);
        this.timeGateway = new TimeGateway();
        this.priceGateway = new PriceGateway();
        this.exchangeInfoGateway = new ExchangeInfoGateway();
        this.accountGateway = new AccountGateway();

        RetryConfig retryConfig = RetryConfig.custom()
                .maxAttempts(3)
                .waitDuration(Duration.ofSeconds(2))
                .retryOnResult(it -> {
                    attempt++;
                    Response response = (Response) it;
                    return response.getStatus() != 200;
                })
                .retryExceptions(Exception.class)
                .build();
        this.retry = Retry.of("limit_retry", retryConfig);
    }

    public OrderAckDTO order(LoginDTO login,
                             LimitOrderAllFreeRequestDTO dto) {
        attempt = 0;
        Supplier<Response> responseSupplier =
                Retry.decorateSupplier(retry, () -> fetch(login, dto));

        try (Response response = responseSupplier.get()) {
            if (response.getStatus() == 200) {
                String json = response.readEntity(String.class);
                return jsonb.fromJson(json, OrderAckDTO.class);
            } else {
                throw new RuntimeException("Error en la respuesta: " + response.getStatus());
            }
        }
    }

    private Response fetch(LoginDTO login,
                           LimitOrderAllFreeRequestDTO dto) {
        BigDecimal quantity = getQuantity(login, dto);
        BigDecimal price = getPrice(dto);
        Map<String, String> map = new HashMap<>();
        map.put("symbol", dto.symbol().toUpperCase(Locale.ROOT));
        map.put("side", dto.side().toString());
        map.put("type", OrderType.LIMIT.toString());
        map.put("price", price.toPlainString());
        map.put("timeInForce", TimeInForce.GTC.toString());
        map.put("quantity", quantity.toPlainString());
        map.put("newOrderRespType", OrderResponseType.ACK.toString());
        map.put("recvWindow", Long.toString(2500L));
        map.put("timestamp", timeGateway.get().serverTime().toString());
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
        Response response = request.post(null); //200

        client.close();
        /*String json = response.readEntity(String.class);
        var result = jsonb.fromJson(json, OrderAckDTO.class);

        return result;*/
        return response;
    }

    private BigDecimal getPrice(LimitOrderAllFreeRequestDTO dto) {
        BigDecimal tickSize = exchangeInfoGateway.get(dto.symbol())
                .symbols()
                .get(0)
                .filters()
                .stream()
                .filter(it -> it.filterType().equalsIgnoreCase("PRICE_FILTER"))
                .findFirst()
                .get()
                .tickSize();
        BigDecimal result = BigDecimal.ZERO;
        while (dto.price().compareTo(result) > 0) {
            result = result.add(tickSize);
        }
        return result;
    }

    private BigDecimal getQuantity(LoginDTO login, LimitOrderAllFreeRequestDTO dto) {
        SymbolDTO symbol = exchangeInfoGateway.get(dto.symbol())
                .symbols()
                .get(0);
        BigDecimal stepSize = symbol.filters()
                .stream()
                .filter(it -> it.filterType().equalsIgnoreCase("LOT_SIZE"))
                .findFirst()
                .get()
                .stepSize();
        if (dto.side().equals(OrderSide.BUY)) {
            BigDecimal price = priceGateway.get(dto.symbol())
                    .price();
            BigDecimal amount = accountGateway.get(login)
                    .balances()
                    .stream()
                    .filter(it -> it.asset().equalsIgnoreCase(symbol.quoteAsset()))
                    .findFirst()
                    .get()
                    .free();
            BigDecimal quantity = amount.divide(price, RoundingMode.DOWN);
            BigDecimal result = BigDecimal.ZERO;
            while (quantity.compareTo(result) > 0) {
                result = result.add(stepSize);
            }
            return result;
        } else {
            BigDecimal amount = accountGateway.get(login)
                    .balances()
                    .stream()
                    .filter(it -> it.asset().equalsIgnoreCase(symbol.baseAsset()))
                    .findFirst()
                    .get()
                    .free();
            BigDecimal result = BigDecimal.ZERO;
            while (amount.compareTo(result) > 0) {
                result = result.add(stepSize);
            }
            return result;
        }
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
