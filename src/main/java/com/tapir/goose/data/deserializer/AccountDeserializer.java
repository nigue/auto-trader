package com.tapir.goose.data.deserializer;

import com.tapir.goose.data.dto.AccountDTO;
import com.tapir.goose.data.dto.BalanceDTO;
import com.tapir.goose.data.dto.CommissionRatesDTO;
import jakarta.json.bind.serializer.DeserializationContext;
import jakarta.json.bind.serializer.JsonbDeserializer;
import jakarta.json.stream.JsonParser;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class AccountDeserializer implements JsonbDeserializer<AccountDTO> {

    @Override
    public AccountDTO deserialize(JsonParser parser,
                                  DeserializationContext ctx,
                                  Type type) {
        String key = null;
        int makerCommission = 0;
        int takerCommission = 0;
        int buyerCommission = 0;
        int sellerCommission = 0;
        CommissionRatesDTO commissionRatesDTO = null;
        Boolean canTrade = null;
        Boolean canWithdraw = null;
        Boolean canDeposit = null;
        Boolean brokered = null;
        Boolean requireSelfTradePrevention = null;
        Boolean preventSor = null;
        long updateTime = 0L;
        String accountType = "";
        List<BalanceDTO> balances = new ArrayList<>();
        long uid = 0L;
        while (parser.hasNext()) {
            JsonParser.Event event = parser.next();
            switch (event) {
                case KEY_NAME -> key = parser.getString();
                case VALUE_STRING -> {
                    if ("accountType".equals(key)) {
                        accountType = parser.getString();
                    }
                }
                case VALUE_NUMBER -> {
                    if ("makerCommission".equals(key)) {
                        makerCommission = parser.getInt();
                    }
                    if ("takerCommission".equals(key)) {
                        takerCommission = parser.getInt();
                    }
                    if ("buyerCommission".equals(key)) {
                        buyerCommission = parser.getInt();
                    }
                    if ("sellerCommission".equals(key)) {
                        sellerCommission = parser.getInt();
                    }
                    if ("updateTime".equals(key)) {
                        updateTime = parser.getLong();
                    }
                    if ("uid".equals(key)) {
                        uid = parser.getLong();
                    }
                }
                case VALUE_FALSE, VALUE_TRUE -> {
                    if ("canTrade".equals(key)) {
                        canTrade = parser.getValue().toString().equals("true");
                    }
                    if ("canWithdraw".equals(key)) {
                        canWithdraw = parser.getValue().toString().equals("true");
                    }
                    if ("canDeposit".equals(key)) {
                        canDeposit = parser.getValue().toString().equals("true");
                    }
                    if ("brokered".equals(key)) {
                        brokered = parser.getValue().toString().equals("true");
                    }
                    if ("requireSelfTradePrevention".equals(key)) {
                        requireSelfTradePrevention = parser.getValue().toString().equals("true");
                    }
                    if ("preventSor".equals(key)) {
                        preventSor = parser.getValue().toString().equals("true");
                    }
                }
                case START_ARRAY -> {
                    if ("balances".equals(key)) {
                        while (parser.hasNext() && parser.next() != JsonParser.Event.END_ARRAY) {
                            balances.add(ctx.deserialize(BalanceDTO.class, parser));
                        }
                    }
                }
                case START_OBJECT -> {
                    if ("commissionRates".equals(key)) {
                        commissionRatesDTO = ctx.deserialize(CommissionRatesDTO.class, parser);
                    }
                }
                default -> {
                }
            }
        }
        return new AccountDTO(makerCommission,
                takerCommission,
                buyerCommission,
                sellerCommission,
                commissionRatesDTO,
                canTrade,
                canWithdraw,
                canDeposit,
                brokered,
                requireSelfTradePrevention,
                preventSor,
                updateTime,
                accountType,
                balances,
                uid);
    }
}
