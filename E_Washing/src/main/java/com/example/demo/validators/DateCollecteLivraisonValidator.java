package com.example.demo.validators;

import com.example.demo.models.Commande;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class DateCollecteLivraisonValidator implements ConstraintValidator<ValidDateCollecteLivraison, Commande> {

    @Override
    public boolean isValid(Commande commande, ConstraintValidatorContext context) {
        if (commande == null) return true; // validation de niveau objet
        if (commande.getDateCollecte() == null || commande.getDateLivraison() == null) return true;

        return !commande.getDateCollecte().after(commande.getDateLivraison());
    }
}
