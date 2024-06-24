package com.example.soporte.DTO.validations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * Validator implementation for {@link NotBlankIfPresent} annotation.
 * Ensures that a string value must not be blank if present.
 */
public class NotBlankIfPresentValidator implements ConstraintValidator<NotBlankIfPresent, String>{
    @Override
    public void initialize(NotBlankIfPresent constraintAnnotation) {
    }

    /**
     * Validates the string value based on the constraint rules.
     *
     * @param s the string value to validate
     * @param constraintValidatorContext the validation context
     * @return true if the value is valid, false only if the string is not null but blank/empty.
     */
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext){
        if (s == null) {
            return true;
        }

        return !s.trim().isEmpty();
    }
}
