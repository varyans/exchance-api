package io.github.varyans.exchange.rate.enumaration;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class EnumCurrencyConverter implements Converter<String,EnumCurrency> {
    @Override
    public EnumCurrency convert(String source) {
        return EnumCurrency.valueOf(source.toUpperCase());
    }
}
