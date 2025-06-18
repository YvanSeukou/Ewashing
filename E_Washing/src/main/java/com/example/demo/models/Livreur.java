package com.example.demo.models;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Livreur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idLivreur;

    @Min(value = 1, message = "Le numéro du livreur doit être supérieur à 0")
    private int numeroLivreur;

    @NotBlank(message = "Le nom du livreur est obligatoire")
    private String nomLivreur;

    @OneToMany(mappedBy = "livreur")
    private List<Commande> commandes;
}
