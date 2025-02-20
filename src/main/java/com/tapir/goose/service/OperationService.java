package com.tapir.goose.service;

import com.tapir.goose.data.dto.AccountDTO;
import com.tapir.goose.data.dto.BalanceDTO;
import com.tapir.goose.data.gateway.AllOrdersGateway;
import com.tapir.goose.view.pojo.OperationVDO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@ApplicationScoped
public class OperationService {

    @Inject
    private AllOrdersGateway allOrdersGateway;
    private List<String> availableAssets = Arrays.asList(
            "BTC",
            "ETH",
            "BNB",
            "LTC",
            "SOL",
            "ADA",
            "DASH",
            "IOTA"
    );

    public OperationVDO process(AccountDTO account) {
        Optional<BalanceDTO> balanceOptional = account.balances()
                .stream()
                .filter(it -> availableAssets.contains(it.asset()))
                .filter(it -> it.locked().compareTo(it.free()) > 0)
                .findFirst();
        String symbol = "";
        BigDecimal market = BigDecimal.ZERO;
        BigDecimal exit = BigDecimal.ZERO;
        BigDecimal now = BigDecimal.ZERO;
        BigDecimal enter = BigDecimal.ZERO;
        if (balanceOptional.isPresent()) {
        }
        return new OperationVDO(balanceOptional.isEmpty(),
                symbol,
                market,
                exit,
                now,
                enter,
                getDate());
    }

    private String getDate() {
        DateFormat df = new SimpleDateFormat("MM/dd HH:mm");
        return df.format(Calendar.getInstance().getTime());
    }
}
