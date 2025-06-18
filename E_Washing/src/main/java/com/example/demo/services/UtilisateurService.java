package com.example.demo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.models.UserRole;
import com.example.demo.models.Utilisateur;
import com.example.demo.repositories.UtilisateurRepository;

@Service
public class UtilisateurService {

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<Utilisateur> showAllUser() {
        return utilisateurRepository.findAll(); 
    }

    public Optional<Utilisateur> showUserById(Long id) {
        return utilisateurRepository.findById(id); 
    }

    public Optional<Utilisateur> showUserByEmail(String email) {
        return utilisateurRepository.findByEmail(email);
    }

    public Utilisateur updateUser(Long id, Utilisateur newClient) {
        return utilisateurRepository.findById(id).map(client -> {
            client.setNomClient(newClient.getNomClient());
            client.setPrenomClient(newClient.getPrenomClient());
            client.setEmail(newClient.getEmail());
            client.setTelephone(newClient.getTelephone());

            // Cryptage du mot de passe si modifié
            if (newClient.getMotDePasse() != null && !newClient.getMotDePasse().isEmpty()) {
                client.setMotDePasse(passwordEncoder.encode(newClient.getMotDePasse()));
            }

            return utilisateurRepository.save(client);
        }).orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
    }

    public Utilisateur createUser(Utilisateur utilisateur) {
        // Vérification si l'email est déjà utilisé
        if (utilisateurRepository.existsByEmail(utilisateur.getEmail())) {
            throw new RuntimeException("Cet email est déjà utilisé.");
        }

        // Définition du rôle par défaut : CLIENT
        if (utilisateur.getTypeUtilisateur() == null) {
            utilisateur.setTypeUtilisateur(UserRole.CLIENT);
        }

        // Cryptage du mot de passe
        utilisateur.setMotDePasse(passwordEncoder.encode(utilisateur.getMotDePasse()));

        return utilisateurRepository.save(utilisateur);
    }

    public void deleteUser(Long id) {
        if (!utilisateurRepository.existsById(id)) {
            throw new RuntimeException("Utilisateur non trouvé !");
        }
        utilisateurRepository.deleteById(id);
    }

    public Utilisateur authenticate(String email, String password) {
        return utilisateurRepository.findByEmail(email)
            .filter(user -> passwordEncoder.matches(password, user.getMotDePasse()))
            .orElseThrow(() -> new RuntimeException("Email ou mot de passe incorrect"));
    }
}
