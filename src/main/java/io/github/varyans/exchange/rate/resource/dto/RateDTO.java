package io.github.varyans.exchange.rate.resource.dto;

import io.github.varyans.exchange.rate.enumaration.EnumCurrency;
import io.github.varyans.exchange.rate.model.Rate;

import java.time.LocalDate;
import java.util.List;

public record RateDTO(EnumCurrency base, LocalDate date, String message, List<Rate> rates) {}
