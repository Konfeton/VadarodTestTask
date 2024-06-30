package com.onkonfeton.vadarod.service;

import com.onkonfeton.vadarod.model.ExchangeRate;

import java.time.LocalDate;
import java.util.Set;

public interface BankApiService {
    Set<ExchangeRate> getExchangeRatesForDate(LocalDate date);

    ExchangeRate getExchangeRateForDateAndCurrencyCode(LocalDate date, String code);
}
