package com.tapir.goose.data.dto;

import java.util.ArrayList;
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

        List<String> orderTypes
) {
}
