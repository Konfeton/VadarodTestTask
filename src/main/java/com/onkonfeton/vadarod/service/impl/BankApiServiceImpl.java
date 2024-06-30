package com.onkonfeton.vadarod.service.impl;

import com.onkonfeton.vadarod.dto.ApiResponse;
import com.onkonfeton.vadarod.exception.BankApiCallException;
import com.onkonfeton.vadarod.model.Currency;
import com.onkonfeton.vadarod.model.ExchangeRate;
import com.onkonfeton.vadarod.service.BankApiService;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

@Component
public class BankApiServiceImpl implements BankApiService {

    private static final String API_URL = "https://api.nbrb.by/exrates/rates";
    private final RestTemplate restTemplate = new RestTemplate();

    public Set<ExchangeRate> getExchangeRatesForDate(LocalDate date) {
        try {
            URI uri = buildUriForDate(date.toString());
            ApiResponse[] response = restTemplate.getForEntity(uri, ApiResponse[].class).getBody();

            Set<ExchangeRate> exchangeRates = new HashSet<>();
            for (ApiResponse apiResponse : response) {
                ExchangeRate exchangeRate = mapToEntity(apiResponse);
                exchangeRates.add(exchangeRate);
            }

            return exchangeRates;
        } catch (Exception e) {
            throw new BankApiCallException("Issues calling api with date: " + date);
        }
    }

    public ExchangeRate getExchangeRateForDateAndCurrencyCode(LocalDate date, String currency) {
        try {
            URI uri = buildUriForDateAndCurrency(date.toString(), currency);
            ApiResponse response = restTemplate.getForEntity(uri, ApiResponse.class).getBody();
            ExchangeRate exchangeRate = mapToEntity(response);
            return exchangeRate;
        } catch (Exception e) {
            throw new BankApiCallException("Issues calling api with date: " + date + " and currency: " + currency);
        }

    }

    private URI buildUriForDate(String date) {
        return URI.create(API_URL
                + "?periodicity=0"
                + "&ondate=" + date);
    }

    private URI buildUriForDateAndCurrency(String date, String currency) {
        return URI.create(API_URL
                + "/" + currency
                + "?parammode=2"
                + "&ondate=" + date);
    }

    private ExchangeRate mapToEntity(ApiResponse apiResponse) {
        Currency currency = new Currency();
        currency.setId(apiResponse.getId());
        currency.setCode(apiResponse.getCode());
        currency.setScale(apiResponse.getScale());
        currency.setName(apiResponse.getName());

        ExchangeRate exchangeRate = new ExchangeRate();
        exchangeRate.setCurrency(currency);
        exchangeRate.setDate(LocalDateTime.parse(apiResponse.getDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")).toLocalDate());
        exchangeRate.setRate(apiResponse.getRate());
        return exchangeRate;
    }


}
