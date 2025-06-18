package com.example.demo.documents;

import java.time.LocalDateTime;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "logs_commande")
public class LogCommande {
    @Id
    private String id;
    private String nom; // nom de l'utilisateur ou du responsable
    private String action; // Exemple : "CREATION", "MODIFICATION"
    private LocalDateTime dateAction;
    private String idCommande; // pour faire le lien avec la commande concernée
}
