package com.example.demo.contollers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.models.Utilisateur;
import com.example.demo.services.JwtService;
import com.example.demo.services.UtilisateurService;

@RestController
@RequestMapping("/Utilisateurs")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class AuthController {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UtilisateurService utilisateurService; // Ajout manquant

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials) {
        String email = credentials.get("email");
        String password = credentials.get("password");

        Utilisateur user = utilisateurService.authenticate(email, password);

        if (user != null) {
            String token = jwtService.generateToken(user.getEmail(), user.getTypeUtilisateur());
            user.setMotDePasse(null); // Suppression du mot de passe dans la réponse

            Map<String, Object> response = new HashMap<>();
            response.put("accessToken", token);
            response.put("user", user);
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(401).body("Email ou mot de passe incorrect");
        }
    }
    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser(@RequestHeader("Authorization") String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(401).body("Token manquant ou invalide");
        }

        String token = authHeader.substring(7);
        if (!jwtService.validateToken(token)) {
            return ResponseEntity.status(401).body("Token invalide");
        }

        String email = jwtService.getEmailFromToken(token);
        Utilisateur utilisateur = utilisateurService.showUserByEmail(email).orElse(null);

        if (utilisateur == null) {
            return ResponseEntity.status(404).body("Utilisateur non trouvé");
        }

        utilisateur.setMotDePasse(null); // Masquer mot de passe
        return ResponseEntity.ok(utilisateur);
    }

}
