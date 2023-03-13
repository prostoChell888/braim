package com.example.if_else.validation.anotation;

import com.example.if_else.validation.validator.FloatNumValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = FloatNumValidator.class)
@Documented
public @interface ValidFloat {

    String message() default "вещественное число не положительное или null";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
