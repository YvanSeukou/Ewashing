package com.example.demo.models;

import java.sql.Timestamp;
import java.util.List;

import com.example.demo.validators.ValidDateCollecteLivraison;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ValidDateCollecteLivraison
@Entity
public class Commande {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCommande;

    @NotBlank(message = "Le numéro de commande est obligatoire")
    private String numeroCommande;

    @NotNull(message = "La date de commande est obligatoire")
    private Timestamp dateCommande;

    @NotNull(message = "La date de collecte est obligatoire")
    private Timestamp dateCollecte;

    @NotNull(message = "La date de livraison est obligatoire")
    private Timestamp dateLivraison;

    @NotBlank(message = "Le statut est obligatoire")
    private String statut;

    @NotBlank(message = "Le montant total est obligatoire")
    private String montantTotal;

    private String adresseLivraison;

    private String instructions;

    @ManyToOne
    @JoinColumn(name = "id_client", nullable = false)
    @JsonIgnoreProperties("commandes") // 🔥 Empêche la boucle infinie
    private Utilisateur client;

    @ManyToOne
    @JoinColumn(name = "id_livreur")
    private Livreur livreur;

    @OneToMany(mappedBy = "commande", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<CommandeService> commandeServices;
}
