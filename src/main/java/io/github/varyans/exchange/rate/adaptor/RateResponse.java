package io.github.varyans.exchange.rate.adaptor;

import io.github.varyans.exchange.rate.enumaration.EnumCurrency;

import java.time.LocalDate;
import java.util.Map;

public record RateResponse(EnumCurrency base,
                           LocalDate date,
                           Map<EnumCurrency, Double> rates,
                           boolean success,
                           long timestamp) {
}
