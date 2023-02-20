package io.github.varyans.exchange.rate.repository;

import io.github.varyans.exchange.rate.adaptor.RateAdaptor;
import io.github.varyans.exchange.rate.adaptor.RateResponse;
import io.github.varyans.exchange.rate.adaptor.RateResponseConverter;
import io.github.varyans.exchange.rate.entity.RateEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Objects;
import static org.assertj.core.api.Assertions.assertThat;
@SpringBootTest
class RateRepositoryTest {

    @Autowired
    RateAdaptor rateAdaptor;

    @Autowired
    RateRepository rateRepository;

    @Autowired
    RateResponseConverter rateResponseConverter;

    @Test
    void dataRetieved_saveToDb() {
        RateResponse rates = rateAdaptor.getRates();
        RateEntity expected = rateResponseConverter.convert(rates);
        Objects.requireNonNull(expected);
        RateEntity actual = rateRepository.save(expected);

        assertThat(actual.getId()).isNotNull();
        assertThat(actual).usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(expected);

    }
}