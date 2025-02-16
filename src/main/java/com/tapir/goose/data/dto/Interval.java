package com.tapir.goose.data.dto;

public enum Interval {

    ONE_SECOND("1s"),
    ONE_MINUTE("1m"),
    TREE_MINUTES("3m"),
    FIVE_MINUTES("5m"),
    FIFTEEN_MINUTES("15m"),
    THIRTY_MINUTES("30m"),
    ONE_HOUR("1h"),
    TWO_HOURS("2h"),
    FOUR_HOURS("4h"),
    SIX_HOURS("6h"),
    EIGHT_HOURS("8h"),
    TWELVE_HOURS("12h"),
    ONE_DAY("1d"),
    TREE_DAYS("3d"),
    ONE_WEEK("1w"),
    ONE_MONTH("1M");

    public final String value;

    Interval(String value) {
        this.value = value;
    }
}
