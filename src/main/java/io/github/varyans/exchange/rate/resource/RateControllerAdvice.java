package io.github.varyans.exchange.rate.resource;

import io.github.varyans.exchange.rate.exceptions.RatesNotFound;
import io.github.varyans.exchange.rate.resource.dto.RateDTO;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDate;
import java.util.Collections;

@RestControllerAdvice
public class RateControllerAdvice {

    @ExceptionHandler(RatesNotFound.class)
    public Object ratesNotFoundHandler(RatesNotFound ex) {
        return new RateDTO(null, LocalDate.now(),"fail", Collections.emptyList());
    }
}
