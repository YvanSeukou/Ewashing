package com.example.demo.models;

import java.util.List;

import com.example.demo.validators.ValidEmail;
import com.example.demo.validators.ValidPassword;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Utilisateur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUtilisateur;

    @NotBlank(message = "Le nom est obligatoire")
    private String nomClient;

    @NotBlank(message = "Le prénom est obligatoire")
    private String prenomClient;

    @ValidEmail
    private String email;

    @NotBlank(message = "Le téléphone est obligatoire")
    private String telephone;

    @ValidPassword
    private String motDePasse;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole typeUtilisateur = UserRole.CLIENT;

    @OneToMany(mappedBy = "client")
    @JsonIgnore  // ← empêche les boucles lors de la sérialisation
    private List<Commande> commandes;

    @OneToMany(mappedBy = "client")
    private List<Avis> avis;
}
