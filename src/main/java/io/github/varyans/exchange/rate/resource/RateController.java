package io.github.varyans.exchange.rate.resource;

import io.github.varyans.exchange.rate.enumaration.EnumCurrency;
import io.github.varyans.exchange.rate.resource.dto.RateDTO;
import io.github.varyans.exchange.rate.service.RateService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("rates")
public class RateController {
    private final RateService rateService;

    public RateController(RateService rateService) {
        this.rateService = rateService;
    }

    @GetMapping
    public RateDTO getRates(@RequestParam(name = "base",required = false) EnumCurrency base,
                            @RequestParam(name = "target",required = false) List<EnumCurrency> target,
                            @RequestParam(name = "date",required = false) @DateTimeFormat(pattern = "dd.MM.yyyy") LocalDate date) {
       return rateService.calculateRates(base, target,date);
    }
}
