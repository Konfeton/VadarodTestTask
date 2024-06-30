package com.onkonfeton.vadarod.dto;

import lombok.Data;

@Data
public class CurrencyDto {
    private Integer id;
    private String code;
    private int scale;
    private String name;
}
