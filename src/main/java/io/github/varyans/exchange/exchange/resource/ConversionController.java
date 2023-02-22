package io.github.varyans.exchange.exchange.resource;

import io.github.varyans.exchange.exchange.entity.ExchangeEntity;
import io.github.varyans.exchange.exchange.service.ExchangeService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("conversion")
public class ConversionController {

    private final ExchangeService exchangeService;

    public ConversionController(ExchangeService exchangeService) {
        this.exchangeService = exchangeService;
    }

    @GetMapping("/{id}")
    public ExchangeEntity getConversion(@PathVariable(name = "id") Long id) {
        return exchangeService.getConversion(id);
    }

    @GetMapping
    public List<ExchangeEntity> getConversionList(@RequestParam(name = "startDate")  LocalDate startDate,
                                                  @RequestParam(name = "endDate") LocalDate endDate) {
        return exchangeService.getConversionList(startDate,endDate);
    }

}
