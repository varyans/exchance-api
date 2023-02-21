package io.github.varyans.exchange.rate.service;

import io.github.varyans.exchange.rate.adaptor.RateAdaptor;
import io.github.varyans.exchange.rate.adaptor.RateResponse;
import io.github.varyans.exchange.rate.adaptor.RateResponseConverter;
import io.github.varyans.exchange.rate.entity.RateEntity;
import io.github.varyans.exchange.rate.enumaration.EnumCurrency;
import io.github.varyans.exchange.rate.exceptions.RatesNotFound;
import io.github.varyans.exchange.rate.model.Rate;
import io.github.varyans.exchange.rate.repository.RateRepository;
import io.github.varyans.exchange.rate.resource.dto.RateDTO;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

@Service
public class RateService {

    private final RateRepository repository;

    private final RateAdaptor rateAdaptor;

    private final RateResponseConverter rateResponseConverter;

    public RateService(RateRepository repository, RateAdaptor rateAdaptor, RateResponseConverter rateResponseConverter) {
        this.repository = repository;
        this.rateAdaptor = rateAdaptor;
        this.rateResponseConverter = rateResponseConverter;
    }

    public RateDTO calculateRates() {
        return calculateRates(null);
    }


    public RateDTO calculateRates(EnumCurrency base) {
        return calculateRates(base, null);
    }


    public RateDTO calculateRates(EnumCurrency base, List<EnumCurrency> targets) {
        return calculateRates(base, targets, null);
    }

    //TODO: Add Cache
    public RateDTO calculateRates(EnumCurrency base, List<EnumCurrency> targets, LocalDate date) {
        EnumCurrency baseCurrency = Optional.ofNullable(base).orElse(EnumCurrency.EUR);
        List<EnumCurrency> enumCurrencies = Arrays.stream(EnumCurrency.values()).toList();
        List<EnumCurrency> targetsCurr = Optional.ofNullable(targets)
                .orElse(enumCurrencies);
        List<EnumCurrency> targetsCurrency = targetsCurr.isEmpty()?enumCurrencies:targetsCurr;
        LocalDate rateDate = Optional.ofNullable(date).orElse(LocalDate.now());

        RateEntity rateEntity = repository.findOneByDate(rateDate)
                .orElseGet(() -> {
                    RateResponse rates = rateAdaptor.getRates();
                    RateEntity entity = rateResponseConverter.convert(rates);
                    return repository.saveAndFlush(entity);
                });

//        RateEntity rateEntity = oneByDate.orElseThrow(RatesNotFound::new);
        Map<EnumCurrency, Double> rates = rateEntity.getRates();

        BigDecimal baseCurrencyRate = BigDecimal.valueOf(rates.get(baseCurrency));

        Function<Map.Entry<EnumCurrency, Double>, Rate> entryToRate = entry
                -> new Rate(
                        entry.getKey(),
                BigDecimal.valueOf(entry.getValue()).divide(baseCurrencyRate,5,RoundingMode.CEILING).doubleValue());

        Predicate<Map.Entry<EnumCurrency, Double>> targetFilter = entry -> targetsCurrency.contains(entry.getKey());

        List<Rate> rateList = rates.entrySet()
                .stream()
                .filter(targetFilter)
                .map(entryToRate)
                .toList();

        return new RateDTO(baseCurrency,rateDate,"success",rateList);
    }
}

