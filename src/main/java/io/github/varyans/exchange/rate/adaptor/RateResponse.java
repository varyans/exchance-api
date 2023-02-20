package io.github.varyans.exchange.rate.adaptor;

import java.time.LocalDate;
import java.util.Map;

public record RateResponse(String base,
                           LocalDate date,
                           Map<String, Double> rates,
                           boolean success,
                           long timestamp) {
}
