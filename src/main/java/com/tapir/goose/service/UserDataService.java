package com.tapir.goose.service;

import com.tapir.goose.data.dto.AccountDTO;
import com.tapir.goose.data.dto.BalanceDTO;
import com.tapir.goose.data.dto.LoginDTO;
import com.tapir.goose.data.gateway.AccountGateway;
import com.tapir.goose.data.gateway.PriceGateway;
import com.tapir.goose.view.pojo.UserVDO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.math.BigDecimal;
import java.util.List;
import java.util.Locale;

@ApplicationScoped
public class UserDataService {

    @Inject
    private PriceGateway priceGateway;

    public UserVDO process(AccountDTO account) {
        List<BalanceDTO> balances = account.balances();
        BigDecimal usdt = balances.stream()
                .filter(it -> it.asset().equalsIgnoreCase("usdt"))
                .findFirst()
                .get()
                .free();
        BigDecimal objective = BigDecimal.ZERO;
        BigDecimal step;
        if (usdt.compareTo(BigDecimal.valueOf(9999L)) > 0) {
            step = BigDecimal.valueOf(500L);
        } else {
            step = BigDecimal.valueOf(5L);
        }
        while (usdt.compareTo(objective) > 0) {
            objective = objective.add(step);
        }
        return new UserVDO("Username",
                "usdt".toUpperCase(Locale.ROOT),
                BigDecimal.valueOf(0.5D),
                objective,
                usdt.setScale(0, BigDecimal.ROUND_DOWN));
    }
}
