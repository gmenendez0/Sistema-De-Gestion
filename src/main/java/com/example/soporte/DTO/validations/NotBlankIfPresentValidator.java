package com.example.soporte.DTO.validations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NotBlankIfPresentValidator implements ConstraintValidator<NotBlankIfPresent, String>{
    @Override
    public void initialize(NotBlankIfPresent constraintAnnotation) {
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext){
        if (s == null) {
            return true;
        }

        return !s.trim().isEmpty();
    }
}
