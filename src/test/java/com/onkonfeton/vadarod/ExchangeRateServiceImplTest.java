package com.onkonfeton.vadarod;


import com.onkonfeton.vadarod.dto.ExchangeRateResponse;
import com.onkonfeton.vadarod.exception.BankApiCallException;
import com.onkonfeton.vadarod.model.Currency;
import com.onkonfeton.vadarod.model.ExchangeRate;
import com.onkonfeton.vadarod.repository.ExchangeRateRepository;
import com.onkonfeton.vadarod.service.BankApiService;
import com.onkonfeton.vadarod.service.CurrencyService;
import com.onkonfeton.vadarod.service.impl.ExchangeRateServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ExchangeRateServiceImplTest {

    @InjectMocks
    private ExchangeRateServiceImpl exchangeRateService;

    @Mock
    private ExchangeRateRepository exchangeRateRepository;

    @Mock
    private BankApiService bankApiService;

    @Mock
    private CurrencyService currencyService;

    @Mock
    private ModelMapper mapper;

    private Currency currency;
    private Currency currency1;
    private ExchangeRate exchangeRate;
    private ExchangeRate exchangeRate1;
    private LocalDate date = LocalDate.of(2023, 6, 30);

    @BeforeEach
    void setUp() {
        currency = new Currency();
        currency.setId(431);
        currency.setCode("USD");
        currency.setName("Доллар США");
        currency.setScale(1);

        exchangeRate = new ExchangeRate();
        exchangeRate.setId(1);
        exchangeRate.setCurrency(currency);
        exchangeRate.setDate(date);
        exchangeRate.setRate(3.1624);

        currency1 = new Currency();
        currency1.setId(452);
        currency1.setCode("PLN");
        currency1.setName("Злотых");
        currency1.setScale(10);

        exchangeRate1 = new ExchangeRate();
        exchangeRate1.setId(2);
        exchangeRate1.setCurrency(currency1);
        exchangeRate1.setDate(date);
        exchangeRate1.setRate(7.8674);

        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getExchangeRatesForDate_shouldReturnExchangeRates_whenDatabaseEmpty() {

        Set<ExchangeRate> exchangeRates = new HashSet<>();
        Set<ExchangeRate> bankExchangeRates = new HashSet<>();

        bankExchangeRates.add(exchangeRate);
        bankExchangeRates.add(exchangeRate1);

        when(exchangeRateRepository.findAllByDate(date)).thenReturn(exchangeRates);
        when(bankApiService.getExchangeRatesForDate(date)).thenReturn(bankExchangeRates);
        when(mapper.map(any(ExchangeRate.class), eq(ExchangeRateResponse.class))).thenReturn(new ExchangeRateResponse());

        List<ExchangeRateResponse> result = exchangeRateService.getExchangeRatesForDate(date);

        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    void getExchangeRatesForDate_shouldReturnExchangeRates_whenDatabaseHasOneRecord() {

        Set<ExchangeRate> exchangeRates = new HashSet<>();
        Set<ExchangeRate> bankExchangeRates = new HashSet<>();

        exchangeRates.add(exchangeRate);

        bankExchangeRates.add(exchangeRate);
        bankExchangeRates.add(exchangeRate1);

        when(exchangeRateRepository.findAllByDate(date)).thenReturn(exchangeRates);
        when(bankApiService.getExchangeRatesForDate(date)).thenReturn(bankExchangeRates);
        when(mapper.map(any(ExchangeRate.class), eq(ExchangeRateResponse.class))).thenReturn(new ExchangeRateResponse());

        List<ExchangeRateResponse> result = exchangeRateService.getExchangeRatesForDate(date);

        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    void getExchangeRatesForDate_shouldReturnExchangeRates_whenDatabaseHasAllRecords() {

        Set<ExchangeRate> exchangeRates = new HashSet<>();
        Set<ExchangeRate> bankExchangeRates = new HashSet<>();

        exchangeRates.add(exchangeRate);
        exchangeRates.add(exchangeRate1);

        bankExchangeRates.add(exchangeRate);
        bankExchangeRates.add(exchangeRate1);

        when(exchangeRateRepository.findAllByDate(date)).thenReturn(exchangeRates);
        when(bankApiService.getExchangeRatesForDate(date)).thenReturn(bankExchangeRates);
        when(mapper.map(any(ExchangeRate.class), eq(ExchangeRateResponse.class))).thenReturn(new ExchangeRateResponse());

        List<ExchangeRateResponse> result = exchangeRateService.getExchangeRatesForDate(date);

        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    void getExchangeRatesForDateAndCurrency_shouldReturnRate_whenDatabaseEmpty() {
        String code = "USD";

        when(currencyService.findByCode(code)).thenReturn(currency);
        when(exchangeRateRepository.findByDateAndCurrency(date, currency)).thenReturn(null);
        when(bankApiService.getExchangeRateForDateAndCurrencyCode(date, code)).thenReturn(exchangeRate);
        when(mapper.map(any(ExchangeRate.class), eq(ExchangeRateResponse.class))).thenReturn(new ExchangeRateResponse());

        ExchangeRateResponse result = exchangeRateService.getExchangeRateForDateAndCurrency(date, code);

        assertNotNull(result);
    }

    @Test
    void getExchangeRatesForDateAndCurrency_shouldReturnRate_whenDatabaseHasRecord() {
        String code = "USD";

        when(currencyService.findByCode(code)).thenReturn(currency);
        when(exchangeRateRepository.findByDateAndCurrency(date, currency)).thenReturn(exchangeRate);
        when(mapper.map(any(ExchangeRate.class), eq(ExchangeRateResponse.class))).thenReturn(new ExchangeRateResponse());

        ExchangeRateResponse result = exchangeRateService.getExchangeRateForDateAndCurrency(date, code);

        assertNotNull(result);
    }

    @Test
    void getExchangeRatesForDateAndCurrency_shouldReturnBankApiException_whenCurrencyCodeWrong() {
        String code = "УФВ";

        when(currencyService.findByCode(code)).thenReturn(currency);
        when(exchangeRateRepository.findByDateAndCurrency(date, currency)).thenReturn(null);
        when(bankApiService.getExchangeRateForDateAndCurrencyCode(date, code)).thenThrow(new BankApiCallException("Invalid code " + code));

        assertThrows(BankApiCallException.class, () -> exchangeRateService.getExchangeRateForDateAndCurrency(date, code));
    }

}
