package com.example.demo.services;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.example.demo.models.UserRole;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;

@Service
public class JwtService {

    // Génération d'une clé sécurisée conforme à HS256
    private static final SecretKey SECRET = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    public String generateToken(String email, UserRole role) {
        return Jwts.builder()
                .setSubject(email)
                .claim("role", role.toString()) // Ajout du rôle ADMINISTRATEUR ou CLIENT dans le token
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // Expire après 24h
                .signWith(SECRET, SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(SECRET).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            System.err.println("Erreur de validation du token : " + e.getMessage());
            return false;
        }
    }

    public String getEmailFromToken(String token) {
        try {
            Claims claims = Jwts.parserBuilder().setSigningKey(SECRET).build().parseClaimsJws(token).getBody();
            return claims.getSubject();
        } catch (Exception e) {
            System.err.println("Erreur lors de la récupération de l'email depuis le token : " + e.getMessage());
            return null;
        }
    }
}
