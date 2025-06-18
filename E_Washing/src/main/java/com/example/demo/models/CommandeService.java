package com.example.demo.models;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class CommandeService {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_commande", nullable = false)
    @JsonBackReference
    private Commande commande;

    @ManyToOne
    @JoinColumn(name = "id_service")
    private Services service;

    private String nomArticle;
    private int quantite;
    private double prixUnitaire;
}
