package io.github.varyans.exchange.rate.adaptor;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RateAdaptor {
    @Qualifier("rateRestTemplate")
    private final RestTemplate restTemplate;


    public RateAdaptor(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public RateResponse getRates(){
        //TODO: use getForEntity and check for http status code
        return restTemplate.getForObject("https://api.apilayer.com/exchangerates_data/latest", RateResponse.class);

    }
}
