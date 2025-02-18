package com.tapir.goose.data.gateway;

import com.tapir.goose.data.deserializer.AccountDeserializer;
import com.tapir.goose.data.dto.AccountDTO;
import com.tapir.goose.data.dto.LoginDTO;
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

import java.util.HashMap;
import java.util.Map;

@ApplicationScoped
public class AccountGateway {

    protected static final String base = "https://api.binance.com/api/v3";

    private final String url;
    private final Jsonb jsonb;
    private final TimeGateway timeGateway;

    public AccountGateway() {
        this.url = base.concat("/account");
        var config = new JsonbConfig().withDeserializers(
                new AccountDeserializer());
        this.jsonb = JsonbBuilder.create(config);
        this.timeGateway = new TimeGateway();
    }

    public AccountDTO get(LoginDTO login) {
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
        WebTarget target = client.target(url.concat(params));

        Invocation.Builder request = target.request(MediaType.APPLICATION_JSON)
                .header("X-MBX-APIKEY", login.key());
        Response response = request.get();
        String json = response.readEntity(String.class);
        AccountDTO result = jsonb.fromJson(json, AccountDTO.class);

        client.close();
        return result;
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
