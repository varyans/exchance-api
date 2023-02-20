package io.github.varyans.exchange.rate.adaptor;

import io.github.varyans.exchange.rate.entity.RateEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class RateResponseConverter implements Converter<RateResponse, RateEntity> {
    @Override
    public RateEntity convert(RateResponse source) {
        RateEntity rateEntity = new RateEntity();
        rateEntity.setRates(source.rates());
        rateEntity.setDate(source.date());
        rateEntity.setBase(source.base());


        return null;
    }
}
