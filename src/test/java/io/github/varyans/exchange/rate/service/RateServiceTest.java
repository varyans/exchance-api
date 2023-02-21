package io.github.varyans.exchange.rate.service;

import io.github.varyans.exchange.rate.enumaration.EnumCurrency;
import io.github.varyans.exchange.rate.exceptions.RatesNotFound;
import io.github.varyans.exchange.rate.model.Rate;
import io.github.varyans.exchange.rate.resource.dto.RateDTO;
import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@SpringBootTest
@Transactional
class RateServiceTest {

    @Autowired
    RateService rateService;

    @Test
    @Sql("/sql_20_02_23.sql")
    void selectedDate_baseUSDwithMultipleTargets_success() {
        LocalDate date = LocalDate.of(2023, 2, 20);
        List<Rate> rates = List.of(new Rate(EnumCurrency.USD, 1.0),
                new Rate(EnumCurrency.TRY,18.86531),
                new Rate(EnumCurrency.EUR,0.93576)
                );
        RateDTO expected = new RateDTO(EnumCurrency.USD, date, "success", rates);

        RateDTO actual = rateService.calculateRates(EnumCurrency.USD, List.of(EnumCurrency.USD,EnumCurrency.TRY,EnumCurrency.EUR), date);

        Assertions.assertThat(actual)
                .usingRecursiveComparison()
                .ignoringCollectionOrder()
                .isEqualTo(expected);
    }

    @Test
    @Sql("/sql_20_02_23.sql")
    void baseNotProvided_baseShouldEur_success() {
        LocalDate date = LocalDate.of(2023, 2, 20);
        List<Rate> rates = List.of(new Rate(EnumCurrency.USD, 1.06867),
                new Rate(EnumCurrency.TRY,20.16062),
                new Rate(EnumCurrency.EUR,1.0)
        );
        RateDTO expected = new RateDTO(EnumCurrency.EUR, date, "success", rates);

        RateDTO actual = rateService.calculateRates(null, List.of(EnumCurrency.USD,EnumCurrency.TRY,EnumCurrency.EUR), date);

        Assertions.assertThat(actual)
                .usingRecursiveComparison()
                .ignoringCollectionOrder()
                .isEqualTo(expected);
    }

    @Test
    @Sql("/sql_20_02_23.sql")
    void targetNotProvided_targetShouldAllEnum_success() {
        LocalDate date = LocalDate.of(2023, 2, 20);
        List<Rate> rates = List.of(new Rate(EnumCurrency.USD, 1.06867),
                new Rate(EnumCurrency.TRY,20.16062),
                new Rate(EnumCurrency.EUR,1.0)
        );
        RateDTO expected = new RateDTO(EnumCurrency.EUR, date, "success", rates);

        RateDTO actual = rateService.calculateRates(null, null, date);

        Assertions.assertThat(actual)
                .usingRecursiveComparison()
                .ignoringFields("rates")
                .ignoringCollectionOrder()
                .isEqualTo(expected);
    }
    @Test
    @Sql("/sql_20_02_23.sql")
    void targetIsEmpty_targetShouldAllEnum_success() {
        LocalDate date = LocalDate.of(2023, 2, 20);
        List<Rate> rates = List.of(new Rate(EnumCurrency.USD, 1.06867),
                new Rate(EnumCurrency.TRY,20.16062),
                new Rate(EnumCurrency.EUR,1.0)
        );
        RateDTO expected = new RateDTO(EnumCurrency.EUR, date, "success", rates);

        RateDTO actual = rateService.calculateRates(null, Collections.emptyList(), date);

        Assertions.assertThat(actual)
                .usingRecursiveComparison()
                .ignoringFields("rates")
                .ignoringCollectionOrder()
                .isEqualTo(expected);
    }

    @Test
    @Sql("/sql_20_02_23.sql")
    void dateNotSet_setCurrentDate_throwRatesNotFound() {
        Assertions
                .assertThatExceptionOfType(RatesNotFound.class)
                .isThrownBy(() -> rateService.calculateRates(EnumCurrency.USD, List.of(EnumCurrency.USD,EnumCurrency.TRY,EnumCurrency.EUR)));
    }
}