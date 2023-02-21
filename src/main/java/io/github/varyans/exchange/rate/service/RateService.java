package io.github.varyans.exchange.rate.service;

import io.github.varyans.exchange.rate.entity.RateEntity;
import io.github.varyans.exchange.rate.repository.RateRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

@Service
public class RateService {

    private final RateRepository repository;

    public RateService(RateRepository repository) {
        this.repository = repository;
    }


    public RateDTO calculateRates(String base, List<String> targets,LocalDate date) {
        Optional<RateEntity> oneByDate = repository.findOneByDate(date);

        RateEntity rateEntity = oneByDate.orElseThrow();
        Map<String, Double> rates = rateEntity.getRates();

        BigDecimal baseCurrencyRate = BigDecimal.valueOf(rates.get(base));

        Function<Map.Entry<String, Double>, Rate> entryToRate = entry
                -> new Rate(
                        entry.getKey(),
                BigDecimal.valueOf(entry.getValue()).divide(baseCurrencyRate,5,RoundingMode.CEILING).doubleValue());

        Predicate<Map.Entry<String, Double>> targetFilter = entry -> targets.contains(entry.getKey());

        List<Rate> rateList = rates.entrySet()
                .stream()
                .filter(targetFilter)
                .map(entryToRate)
                .toList();

        return new RateDTO(base,date,"success",rateList);
    }


    public RateDTO calculateRates(String base, List<String> targets) {
        LocalDate date = LocalDate.now();
        return calculateRates(base, targets,date);
    }
}

record RateDTO(String base,LocalDate date, String message, List<Rate> rates) {}
record Rate(String currency, Double rate) {}