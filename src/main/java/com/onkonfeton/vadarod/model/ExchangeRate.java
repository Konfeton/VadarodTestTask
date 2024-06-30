package com.onkonfeton.vadarod.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table (name = "exchange_rates",
        indexes = {@Index(name = "idx_date", columnList = "date")}
)

@Getter
@Setter
public class ExchangeRate {
    @Id
    @GeneratedValue (strategy = GenerationType.SEQUENCE)
    private Integer id;
    @ManyToOne
    @JoinColumn (name = "currency_id")
    private Currency currency;
    @Column(name = "date", nullable = false)
    private LocalDate date;
    @Column(name = "rate", nullable = false)
    private Double rate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExchangeRate that = (ExchangeRate) o;
        return Objects.equals(currency, that.currency) && Objects.equals(date, that.date) && Objects.equals(rate, that.rate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(currency, date, rate);
    }
}
