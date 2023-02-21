package io.github.varyans.exchange.rate.resource;

import io.github.varyans.exchange.rate.enumaration.EnumCurrency;
import io.github.varyans.exchange.rate.resource.dto.RateDTO;
import io.github.varyans.exchange.rate.service.RateService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RateController.class)
class RateControllerTest {
    public static final String INVALID_CURRENCY = "123";
    @Autowired
    MockMvc mockMvc;

    @MockBean
    RateService rateService;

    @Test
    void unknownCurrencyInSource_ShouldReturn_Error() throws Exception {
        mockMvc.perform(get("/rates")
                        .queryParam("base", INVALID_CURRENCY)
                        .queryParam("target", "USD", "EUR"))
                .andDo(print())
                .andExpect(status().is4xxClientError());

    }

    @Test
    void unknownCurrencyInTarget_ShouldReturn_Error() throws Exception {
        mockMvc.perform(get("/rates")
                        .queryParam("base", "TRY")
                        .queryParam("target", INVALID_CURRENCY, "EUR"))
                .andDo(print())
                .andExpect(status().is4xxClientError());

    }


    @Test
    void validCurrencyInSourceInLowerCase_ShouldReturn_Success() throws Exception {
        when(rateService.calculateRates(EnumCurrency.TRY, List.of(EnumCurrency.USD,EnumCurrency.EUR)))
                .thenReturn(new RateDTO(EnumCurrency.TRY, LocalDate.now(),"", Collections.emptyList()));

        mockMvc.perform(get("/rates")
                        .queryParam("base", "try")
                        .queryParam("target", "USD", "EUR"))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());
    }
}