package com.tapir.goose.data.gateway;

import com.tapir.goose.data.deserializer.AccountDeserializer;
import com.tapir.goose.data.dto.*;
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
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@ApplicationScoped
public class LimitOrderGateway {

    protected static final String base = "https://api.binance.com/api/v3";

    private final String url;
    private final Jsonb jsonb;
    private final TimeGateway timeGateway;
    private final ExchangeInfoGateway exchangeInfoGateway;
    private final PriceGateway priceGateway;
    private final AccountGateway accountGateway;

    public LimitOrderGateway() {
        this.url = base.concat("/order");
        var config = new JsonbConfig().withDeserializers(
                new AccountDeserializer());
        this.jsonb = JsonbBuilder.create(config);
        this.timeGateway = new TimeGateway();
        this.priceGateway = new PriceGateway();
        this.exchangeInfoGateway = new ExchangeInfoGateway();
        this.accountGateway = new AccountGateway();
    }

    public OrderAckDTO order(LoginDTO login,
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
        WebTarget target = client.target(url.concat(params));

        Invocation.Builder request = target.request(MediaType.APPLICATION_JSON)
                .header("X-MBX-APIKEY", login.key());
        Response response = request.post(null);
        String json = response.readEntity(String.class);
        var result = jsonb.fromJson(json, OrderAckDTO.class);

        client.close();
        return result;
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
