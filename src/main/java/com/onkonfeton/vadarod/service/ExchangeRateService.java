package com.onkonfeton.vadarod.service;

import com.onkonfeton.vadarod.dto.ExchangeRateResponse;

import java.time.LocalDate;
import java.util.List;

public interface ExchangeRateService {
    List<ExchangeRateResponse> getExchangeRatesForDate(LocalDate date);

    ExchangeRateResponse getExchangeRateForDateAndCurrency(LocalDate date, String currency);
}
