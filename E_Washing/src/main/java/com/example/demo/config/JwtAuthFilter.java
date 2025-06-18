package com.example.demo.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.demo.models.Utilisateur;
import com.example.demo.models.UserRole;
import com.example.demo.services.JwtService;
import com.example.demo.services.UtilisateurService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UtilisateurService utilisateurService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            chain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(7);

        try {
            if (!jwtService.validateToken(token)) {
                System.err.println("Token invalide !");
                chain.doFilter(request, response);
                return;
            }

            String email = jwtService.getEmailFromToken(token);

            if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                Utilisateur utilisateur = utilisateurService.showUserByEmail(email).orElse(null);

                if (utilisateur != null) {
                    // Conversion explicite du rôle en `UserRole`
                    UserRole role;
                    try {
                        role = UserRole.valueOf(utilisateur.getTypeUtilisateur().toString());
                    } catch (IllegalArgumentException e) {
                        System.err.println("Erreur : rôle inconnu " + utilisateur.getTypeUtilisateur());
                        chain.doFilter(request, response);
                        return;
                    }

                    UserDetails userDetails = User.withUsername(utilisateur.getEmail())
                            .password(utilisateur.getMotDePasse())
                            .roles(role.name()) // Ajout du rôle correctement formaté
                            .build();

                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
        } catch (Exception e) {
            System.err.println("Erreur dans la gestion du JWT : " + e.getMessage());
        }

        chain.doFilter(request, response);
    }
}
