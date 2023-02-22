package io.github.varyans.exchange.exchange.service;

import io.github.varyans.exchange.exchange.entity.ExchangeEntity;
import io.github.varyans.exchange.exchange.repository.ExchangeRepository;
import io.github.varyans.exchange.rate.enumaration.EnumCurrency;
import io.github.varyans.exchange.rate.exceptions.RatesNotFound;
import io.github.varyans.exchange.rate.model.Rate;
import io.github.varyans.exchange.rate.resource.dto.RateDTO;
import io.github.varyans.exchange.rate.service.RateService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ExchangeService {

    private final RateService rateService;

    private final ExchangeRepository exchangeRepository;

    public ExchangeService(RateService rateService, ExchangeRepository exchangeRepository) {
        this.rateService = rateService;
        this.exchangeRepository = exchangeRepository;
    }

    public ExchangeEntity calculateExchangeRate(Double amount,EnumCurrency base, List<EnumCurrency> targets) {
        RateDTO rateDTO = rateService.calculateRates(base,targets);


        Map<EnumCurrency, Double> exchange = rateDTO.rates()
                .stream()
                .map(rate -> new Rate(rate.currency(), BigDecimal.valueOf(rate.rate()).multiply(BigDecimal.valueOf(amount), new MathContext(5, RoundingMode.CEILING)).doubleValue()))
                .collect(Collectors.toMap(Rate::currency,Rate::rate));

        ExchangeEntity exchangeEntity = new ExchangeEntity();
        exchangeEntity.setBase(rateDTO.base());
        exchangeEntity.setAmount(amount);
        exchangeEntity.setDate(LocalDate.now());
        exchangeEntity.setRates(exchange);

        ExchangeEntity saved = exchangeRepository.saveAndFlush(exchangeEntity);

        return saved;
    }

    public ExchangeEntity getConversion(Long id) {
        return exchangeRepository.findById(id).orElseThrow(RatesNotFound::new);
    }

    public List<ExchangeEntity> getConversionList(LocalDate startDate, LocalDate endDate) {
        return exchangeRepository.findByDateBetween(startDate, endDate);
    }
}
