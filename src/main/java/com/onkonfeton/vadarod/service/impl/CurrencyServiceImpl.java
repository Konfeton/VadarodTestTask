package com.onkonfeton.vadarod.service.impl;

import com.onkonfeton.vadarod.model.Currency;
import com.onkonfeton.vadarod.repository.CurrencyRepository;
import com.onkonfeton.vadarod.service.CurrencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CurrencyServiceImpl implements CurrencyService {

    private final CurrencyRepository currencyRepository;

    public void save(Currency currency) {
        currencyRepository.save(currency);
    }

    public Currency findByCode(String code) {
        return currencyRepository.findByCode(code);
    }

    public boolean isExistsById(Integer id) {
        return currencyRepository.existsById(id);
    }


}
