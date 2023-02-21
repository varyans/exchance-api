package io.github.varyans.exchange.rate.model;

import io.github.varyans.exchange.rate.enumaration.EnumCurrency;

public record Rate(EnumCurrency currency, Double rate) {}
