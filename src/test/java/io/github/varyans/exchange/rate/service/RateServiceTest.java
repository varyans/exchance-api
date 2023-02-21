package io.github.varyans.exchange.rate.service;

import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest
@Transactional
class RateServiceTest {

    @Autowired
    RateService rateService;

    @Test
    @Sql("/sql_20_02_23.sql")
    void name() {
        LocalDate date = LocalDate.of(2023, 2, 20);
        List<Rate> rates = List.of(new Rate("USD", 1.0),
                new Rate("TRY",18.86531),
                new Rate("EUR",0.93576)
                );
        RateDTO expected = new RateDTO("USD", date, "success", rates);

        RateDTO actual = rateService.calculateRates("USD", List.of("TRY", "EUR", "USD"), date);

        Assertions.assertThat(actual).isEqualTo(expected);
    }
}