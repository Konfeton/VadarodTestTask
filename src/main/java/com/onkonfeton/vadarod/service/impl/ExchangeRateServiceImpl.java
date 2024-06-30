package com.onkonfeton.vadarod.service.impl;

import com.onkonfeton.vadarod.dto.ExchangeRateResponse;
import com.onkonfeton.vadarod.model.Currency;
import com.onkonfeton.vadarod.model.ExchangeRate;
import com.onkonfeton.vadarod.repository.ExchangeRateRepository;
import com.onkonfeton.vadarod.service.BankApiService;
import com.onkonfeton.vadarod.service.CurrencyService;
import com.onkonfeton.vadarod.service.ExchangeRateService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ExchangeRateServiceImpl implements ExchangeRateService {

    private final ExchangeRateRepository exchangeRateRepository;
    private final BankApiService bankApiService;
    private final CurrencyService currencyService;
    private final ModelMapper mapper;


    public List<ExchangeRateResponse> getExchangeRatesForDate(LocalDate date) {
        Set<ExchangeRate> exchangeRates = exchangeRateRepository.findAllByDate(date);
        Set<ExchangeRate> bankExchangeRates = bankApiService.getExchangeRatesForDate(date);
        Set<ExchangeRate> differences = new HashSet<>(bankExchangeRates);

        if (exchangeRates.isEmpty() || !exchangeRates.containsAll(bankExchangeRates)) {

            differences.removeAll(exchangeRates);

            exchangeRates = bankExchangeRates;

            addMissingCurrenciesToDatabase(differences);
            addMissingRatesToDatabase(differences);
        }

        List<ExchangeRateResponse> responseRates = mapToDto(exchangeRates);

        return responseRates;
    }

    public ExchangeRateResponse getExchangeRateForDateAndCurrency(LocalDate date, String code) {
        Currency currency = currencyService.findByCode(code);
        ExchangeRate exchangeRate = exchangeRateRepository.findByDateAndCurrency(date, currency);
        if (exchangeRate == null) {
            exchangeRate = bankApiService.getExchangeRateForDateAndCurrencyCode(date, code);
            if (currency == null) {
                currencyService.save(exchangeRate.getCurrency());
            }
            if (!isExchangeRateExist(date, exchangeRate)) {
                save(exchangeRate);
            }
        }
        ExchangeRateResponse response = mapper.map(exchangeRate, ExchangeRateResponse.class);

        return response;

    }

    private void addMissingCurrenciesToDatabase(Set<ExchangeRate> differences) {
        for (ExchangeRate exchangeRate : differences) {
            Currency currency = exchangeRate.getCurrency();
            if (!isCurrencyExist(currency)) {
                currencyService.save(currency);
            }
        }
    }

    private void addMissingRatesToDatabase(Set<ExchangeRate> differences) {
        for (ExchangeRate exchangeRate : differences) {
            save(exchangeRate);
        }
    }

    private boolean isCurrencyExist(Currency currency) {
        return currencyService.isExistsById(currency.getId());
    }

    private void save(ExchangeRate exchangeRate) {
        exchangeRateRepository.save(exchangeRate);
    }

    private boolean isExchangeRateExist(LocalDate date, ExchangeRate exchangeRate) {
        return exchangeRateRepository.findByDateAndCurrency(date, exchangeRate.getCurrency()) != null;
    }

    private List<ExchangeRateResponse> mapToDto(Set<ExchangeRate> exchangeRates) {
        return exchangeRates.stream()
                .map(el -> mapper.map(el, ExchangeRateResponse.class))
                .toList();
    }

}
