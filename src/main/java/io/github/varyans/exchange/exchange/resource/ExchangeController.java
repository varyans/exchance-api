package io.github.varyans.exchange.exchange.resource;

import io.github.varyans.exchange.exchange.entity.ExchangeEntity;
import io.github.varyans.exchange.exchange.service.ExchangeService;
import io.github.varyans.exchange.rate.enumaration.EnumCurrency;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("exchange")
public class ExchangeController {

    private final ExchangeService exchangeService;

    public ExchangeController(ExchangeService exchangeService) {
        this.exchangeService = exchangeService;
    }

    @GetMapping
    public ExchangeEntity getRates(@RequestParam(name = "base",required = false) EnumCurrency base,
                                   @RequestParam(name = "target",required = false) List<EnumCurrency> target,
                                   @RequestParam(name = "amount")  Double amount) {
        return exchangeService.calculateExchangeRate(amount,base, target);
    }
}
