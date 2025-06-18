package com.example.demo.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.models.Utilisateur;

public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {
    boolean existsByEmail(String email);
    Optional<Utilisateur> findByEmail(String email);
}
