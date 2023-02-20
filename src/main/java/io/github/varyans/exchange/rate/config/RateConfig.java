package io.github.varyans.exchange.rate.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RateConfig {

    @Bean(name = "rateRestTemplate")
    public RestTemplate getRateRestTemplate(@Autowired @Value("${exchange.rates.api.key}") String apiKey) {
        return new RestTemplateBuilder()
                .defaultHeader("apiKey",apiKey)
                .build();
    }

}
