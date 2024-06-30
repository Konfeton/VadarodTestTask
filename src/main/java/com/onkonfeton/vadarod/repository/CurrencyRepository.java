package com.onkonfeton.vadarod.repository;

import com.onkonfeton.vadarod.model.Currency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrencyRepository extends JpaRepository<Currency, Integer> {
    Currency findByCode(String code);
}
