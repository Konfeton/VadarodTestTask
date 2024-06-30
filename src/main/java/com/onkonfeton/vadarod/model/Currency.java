package com.onkonfeton.vadarod.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Entity
@Table (name = "currencies",
        indexes = {@Index(name = "idx_code", columnList = "code")}
)
@Getter
@Setter
public class Currency {
    @Id
    private Integer id;
    @Column(name = "code", unique = true, nullable = false)
    private String code;
    @Column(name = "scale", nullable = false)
    private int scale;
    @Column(name = "name", nullable = false)
    private String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Currency currency = (Currency) o;
        return scale == currency.scale && Objects.equals(id, currency.id) && Objects.equals(code, currency.code) && Objects.equals(name, currency.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, code, scale, name);
    }
}
