package com.onkonfeton.vadarod.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ExchangeRateResponse {
    private CurrencyDto currency;
    private LocalDate date;
    private Double rate;
}
