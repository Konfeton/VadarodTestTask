package com.onkonfeton.vadarod.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ExceptionMessage {
    private int errorCode;
    private String message;
}
