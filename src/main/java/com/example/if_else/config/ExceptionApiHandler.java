package com.example.if_else.config;

import com.example.if_else.exeptions.ApiErrorException;
import com.example.if_else.exeptions.ApiErrorResponse;
import com.example.if_else.exeptions.NotFoundException;
import com.example.if_else.utils.errors.ValidationErrorResponse;
import com.example.if_else.utils.errors.Violation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ExceptionApiHandler {


    @ExceptionHandler({ ConstraintViolationException.class, MethodArgumentNotValidException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ApiErrorResponse onMethodArgumentNotValidException(RuntimeException e) {
        return new ApiErrorResponse(
                "Not valid value request",
                "400",
                e.getClass().getSimpleName(),
                e.getMessage(),
                Arrays.stream(e.getStackTrace()).map(StackTraceElement::toString)
                        .toArray(String[]::new)
        );
    }



    @ExceptionHandler(ApiErrorException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ApiErrorResponse apiErrorExceptionHandler(ApiErrorException ex) {
        return new ApiErrorResponse(
                "Error in Api",
                "400",
                ex.getClass().getSimpleName(),
                ex.getMessage(),
                Arrays.stream(ex.getStackTrace())
                        .map(StackTraceElement::toString)
                        .toArray(String[]::new));

    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ApiErrorResponse apiErrorExceptionHandler(NumberFormatException ex) {
        return new ApiErrorResponse(
                "Not found",
                "404",
                ex.getClass().getSimpleName(),
                ex.getMessage(),
                Arrays.stream(ex.getStackTrace())
                        .map(StackTraceElement::toString)
                        .toArray(String[]::new));
    }
}