package com.onkonfeton.vadarod.service;

import com.onkonfeton.vadarod.model.Currency;

public interface CurrencyService {
    void save(Currency currency);

    Currency findByCode(String code);

    boolean isExistsById(Integer id);
}
