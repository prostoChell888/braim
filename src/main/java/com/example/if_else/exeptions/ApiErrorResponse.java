package com.example.if_else.exeptions;


import lombok.Data;

@Data
public final class ApiErrorResponse {
    private final String description;
    private final String code;
    private final String exceptionName;
    private final String exceptionMessage;
    private final String[] stacktrace;
        }