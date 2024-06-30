package com.onkonfeton.vadarod.controller;

import com.onkonfeton.vadarod.dto.ExchangeRateResponse;
import com.onkonfeton.vadarod.service.ExchangeRateService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/exchangeRates")
@RequiredArgsConstructor
public class ExchangeRateController {


    private final ExchangeRateService exchangeRateServiceImpl;

    @GetMapping("")
    public List<ExchangeRateResponse> getExchangeRates(@RequestParam(required = false) LocalDate date) {

        if (date == null) {
            date = LocalDate.now();
        }

        return exchangeRateServiceImpl.getExchangeRatesForDate(date);
    }

    @GetMapping("/{currency}")
    public ExchangeRateResponse getExchangeRateForDateAndCurrency(@PathVariable String currency, @RequestParam(required = false) LocalDate date) {
        if (date == null) {
            date = LocalDate.now();
        }
        return exchangeRateServiceImpl.getExchangeRateForDateAndCurrency(date, currency);
    }
}
