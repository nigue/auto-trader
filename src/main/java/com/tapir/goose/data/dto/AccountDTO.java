package com.tapir.goose.data.dto;

import java.util.List;

public record AccountDTO(
        Integer makerCommission,
        Integer takerCommission,
        Integer buyerCommission,
        Integer sellerCommission,
        CommissionRatesDTO commissionRates,
        Boolean canTrade,
        Boolean canWithdraw,
        Boolean canDeposit,
        Boolean brokered,
        Boolean requireSelfTradePrevention,
        Boolean preventSor,
        Long updateTime,
        String accountType,
        List<BalanceDTO> balances,
        Long uid
) {
}
