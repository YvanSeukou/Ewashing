package com.example.demo.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;
import java.util.List;

public class UserTypeValidator implements ConstraintValidator<ValidUserType, String> {
    private static final List<String> VALID_TYPES = Arrays.asList("utilisateur", "admin", "livreur");

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value != null && VALID_TYPES.contains(value.toLowerCase());
    }
}