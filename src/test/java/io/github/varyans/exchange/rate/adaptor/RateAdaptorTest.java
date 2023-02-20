package io.github.varyans.exchange.rate.adaptor;

import io.github.varyans.exchange.rate.config.RateConfig;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {RateConfig.class, RateAdaptor.class})
class RateAdaptorTest {

    @Autowired
    private RateAdaptor rateAdaptor;

    @Test
    void rateServiceCall_success() {
        String expected = "EUR";
        Object actual = rateAdaptor.getRates();
        System.out.println(actual);
        Assertions.assertThat(actual).isNotNull();
    }

    //TODO: Improve with toxiProxy
}