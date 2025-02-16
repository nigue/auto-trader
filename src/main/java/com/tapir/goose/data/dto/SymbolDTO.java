package com.tapir.goose.data.dto;

import java.util.List;

public record SymbolDTO(
        String symbol,
        String status,
        String baseAsset,
        Integer baseAssetPrecision,
        String quoteAsset,
        Integer quotePrecision,
        Integer quoteAssetPrecision,
        Integer baseCommissionPrecision,
        Integer quoteCommissionPrecision,

        List<String> orderTypes,
        Boolean icebergAllowed,
        Boolean ocoAllowed,
        Boolean otoAllowed,
        Boolean quoteOrderQtyMarketAllowed,
        Boolean allowTrailingStop,
        Boolean cancelReplaceAllowed,
        Boolean isSpotTradingAllowed,
        Boolean isMarginTradingAllowed,
        List<FilterDTO> filters
) {
}
