package com.example.if_else.validation.validator;

import com.example.if_else.validation.anotation.ValidFloat;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

public class FloatNumValidator implements ConstraintValidator<ValidFloat, Float> {
    @Override
    public boolean isValid(Float num, ConstraintValidatorContext context) {
        return !Objects.isNull(num) && num > 0;
    }
}
