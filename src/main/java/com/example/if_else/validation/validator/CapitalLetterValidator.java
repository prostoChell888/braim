package com.example.if_else.validation.validator;

import com.example.if_else.validation.anotation.ValidElOfList;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;



public class CapitalLetterValidator implements ConstraintValidator <ValidElOfList, List<Long>> {
    @Override
    public boolean isValid(List<Long> list, ConstraintValidatorContext context) {
       return list.size() > 0
               && !list.contains(null)
               && list.stream().allMatch(el -> el > 0);
    }
}
