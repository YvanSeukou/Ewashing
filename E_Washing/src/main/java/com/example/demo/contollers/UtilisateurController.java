package com.example.demo.contollers;

import java.util.List;
import java.util.Optional;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import com.example.demo.models.Utilisateur;
import com.example.demo.services.UtilisateurService;

@RestController
@RequestMapping("/Utilisateurs")
@CrossOrigin(origins = "http://localhost:5173")
public class UtilisateurController {

    @Autowired
    private UtilisateurService utilisateurService;

    // Classe interne pour structurer les messages d'erreur
    static class ErrorResponse {
        private String message;

        public ErrorResponse(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }

    @PostMapping("/register")
    @Transactional
    public ResponseEntity<?> registerUser(@RequestBody Utilisateur utilisateur) {
        try {
            Utilisateur savedUser = utilisateurService.createUser(utilisateur);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                 .body(new ErrorResponse(e.getMessage()));
        }
    }

    @PreAuthorize("hasRole('ADMINISTRATEUR')")
    @GetMapping("/all")
    public List<Utilisateur> getAllClients() {
        return utilisateurService.showAllUser();
    }

    @PreAuthorize("hasRole('ADMINISTRATEUR')")
    @GetMapping("/{id}")
    public Optional<Utilisateur> getClientById(@PathVariable Long id) {
        return utilisateurService.showUserById(id);
    }

    @PreAuthorize("hasRole('ADMINISTRATEUR')")
    @PutMapping("/update/{id}")
    @Transactional
    public ResponseEntity<Utilisateur> updateClient(@PathVariable Long id, @RequestBody Utilisateur client) {
        try {
            Utilisateur updatedUser = utilisateurService.updateUser(id, client);
            return ResponseEntity.status(HttpStatus.OK).body(updatedUser);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PreAuthorize("hasRole('ADMINISTRATEUR')")
    @DeleteMapping("/supp/{id}")
    @Transactional
    public ResponseEntity<String> deleteClient(@PathVariable Long id) {
        try {
            utilisateurService.deleteUser(id);
            return ResponseEntity.status(HttpStatus.OK).body("Utilisateur supprimé avec succès.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Utilisateur non trouvé !");
        }
    }
}
