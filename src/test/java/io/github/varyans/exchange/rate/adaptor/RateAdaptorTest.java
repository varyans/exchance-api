package io.github.varyans.exchange.rate.adaptor;

import io.github.varyans.exchange.rate.config.RateConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = {RateConfig.class, RateAdaptor.class})
class RateAdaptorTest {

    @Autowired
    private RateAdaptor rateAdaptor;

    @Test
    void rateServiceCall_success() {
        String expected = "EUR";
        Object actual = rateAdaptor.getRates();
        assertThat(actual).isNotNull();
    }

    //TODO: Improve with toxiProxy
}