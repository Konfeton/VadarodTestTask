package com.onkonfeton.vadarod.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ApiResponse {

    @JsonProperty("Cur_ID")
    private int id;
    @JsonProperty("Date")
    private String date;
    @JsonProperty("Cur_Abbreviation")
    private String code;
    @JsonProperty("Cur_Scale")
    private int scale;
    @JsonProperty("Cur_Name")
    private String name;
    @JsonProperty("Cur_OfficialRate")
    private double rate;

}