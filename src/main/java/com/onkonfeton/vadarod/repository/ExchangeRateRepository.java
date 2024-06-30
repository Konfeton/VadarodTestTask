package com.onkonfeton.vadarod.repository;

import com.onkonfeton.vadarod.model.Currency;
import com.onkonfeton.vadarod.model.ExchangeRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface ExchangeRateRepository extends JpaRepository<ExchangeRate, Integer> {
    Set<ExchangeRate> findAllByDate(LocalDate date);

    ExchangeRate findByDateAndCurrency(LocalDate date, Currency currency);
}
