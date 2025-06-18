package com.example.demo.contollers;

import java.util.List;
import java.util.Optional;

import com.example.demo.models.Commande;
import com.example.demo.models.CommandeService;
import com.example.demo.models.Utilisateur;
import com.example.demo.services.CommandeServiceService;
import com.example.demo.services.JwtService;
import com.example.demo.services.UtilisateurService;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/commandes")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class CommandeController {

    @Autowired
    private CommandeServiceService commandeService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UtilisateurService utilisateurService;

    @GetMapping
    public List<Commande> getAllCommandes() {
        return commandeService.showAllCommandes();
    }

    @GetMapping("/client/{id}")
    public List<Commande> getCommandesByClient(@PathVariable Long id) {
        return commandeService.findCommandesByClient(id);
    }

    @GetMapping("/mes-commandes")
    public List<Commande> getCommandesPourUtilisateur(HttpServletRequest request) {
        String auth = request.getHeader("Authorization");
        if (auth == null || !auth.startsWith("Bearer ")) {
            throw new RuntimeException("Token JWT non fourni");
        }
        String email = jwtService.getEmailFromToken(auth.substring(7));
        Utilisateur client = utilisateurService
            .showUserByEmail(email)
            .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
        return commandeService.findCommandesByClient(client.getIdUtilisateur());
    }

    @GetMapping("/{id}")
    public Optional<Commande> getCommandeById(@PathVariable Long id) {
        return commandeService.showCommandeById(id);
    }

    @PutMapping("/{id}")
    public Commande updateCommande(
        @PathVariable Long id,
        @RequestBody Commande commande
    ) {
        return commandeService.updateCommande(id, commande);
    }

    @PostMapping("/add")
    public Commande createCommande(
        @RequestBody Commande commande,
        HttpServletRequest request
    ) {
        String auth = request.getHeader("Authorization");
        if (auth == null || !auth.startsWith("Bearer ")) {
            throw new RuntimeException("Token JWT non fourni");
        }
        String email = jwtService.getEmailFromToken(auth.substring(7));
        Utilisateur client = utilisateurService
            .showUserByEmail(email)
            .orElseThrow(() -> new RuntimeException("Client non trouvé"));

        commande.setClient(client);

        // Associer les articles
        if (commande.getCommandeServices() != null) {
            for (CommandeService cs : commande.getCommandeServices()) {
                cs.setCommande(commande);
            }
        }

        return commandeService.createCommande(commande);
    }

    @DeleteMapping("/{id}")
    public void deleteCommande(@PathVariable Long id) {
        commandeService.deleteCommande(id);
    }
}
