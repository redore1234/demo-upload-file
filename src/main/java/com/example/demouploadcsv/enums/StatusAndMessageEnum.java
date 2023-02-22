package com.example.demouploadcsv.enums;

import lombok.Getter;

@Getter
public enum StatusAndMessageEnum {
    SUCCESS(200, "Success");
    private final Integer statusCode;
    private final String message;

    StatusAndMessageEnum(Integer statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }
}
