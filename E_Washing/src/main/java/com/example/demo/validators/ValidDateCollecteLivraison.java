package com.example.demo.validators;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Documented
@Constraint(validatedBy = DateCollecteLivraisonValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidDateCollecteLivraison {
    String message() default "La date de collecte ne peut pas être après la date de livraison";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
