package com.example.if_else.exeptions;

public class ApiErrorException extends RuntimeException
{
    public ApiErrorException(String message)
    {
        super(message);
    }
}
