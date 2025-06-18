package com.example.demo.services;

import com.example.demo.models.UserRole;
import com.example.demo.models.Utilisateur;
import com.example.demo.repositories.UtilisateurRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
class UtilisateurServiceTest {

    @Mock
    private UtilisateurRepository utilisateurRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UtilisateurService utilisateurService;

    private Utilisateur utilisateur;

    @BeforeEach
    void setUp() {
        utilisateur = new Utilisateur();
        utilisateur.setIdUtilisateur(1L); 
        utilisateur.setEmail("test@example.com");
        utilisateur.setMotDePasse("password123");
        utilisateur.setNomClient("Max");
        utilisateur.setPrenomClient("Aldo");
        utilisateur.setTelephone("0600000000");
    }

    @Test
    void testCreateUser_success() {
        when(utilisateurRepository.existsByEmail("test@example.com")).thenReturn(false);
        when(passwordEncoder.encode("password123")).thenReturn("encodedPassword");
        when(utilisateurRepository.save(any(Utilisateur.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Utilisateur result = utilisateurService.createUser(utilisateur);

        assertEquals("test@example.com", result.getEmail());
        assertEquals("encodedPassword", result.getMotDePasse());
        assertEquals(UserRole.CLIENT, result.getTypeUtilisateur());
        verify(utilisateurRepository).save(result);
    }

    @Test
    void testCreateUser_emailAlreadyExists_shouldThrowException() {
        when(utilisateurRepository.existsByEmail("test@example.com")).thenReturn(true);

        RuntimeException ex = assertThrows(RuntimeException.class, () ->
                utilisateurService.createUser(utilisateur));

        assertEquals("Cet email est déjà utilisé.", ex.getMessage());
    }

    @Test
    void testDeleteUser_success() {
        Long userId = 1L;
        when(utilisateurRepository.existsById(userId)).thenReturn(true);

        utilisateurService.deleteUser(userId);

        verify(utilisateurRepository).deleteById(userId);
    }

    @Test
    void testDeleteUser_notFound_shouldThrowException() {
        Long userId = 1L;
        when(utilisateurRepository.existsById(userId)).thenReturn(false);

        RuntimeException ex = assertThrows(RuntimeException.class, () ->
                utilisateurService.deleteUser(userId));

        assertEquals("Utilisateur non trouvé !", ex.getMessage());
    }

    @Test
    void testUpdateUser_success() {
        Utilisateur updated = new Utilisateur();
        updated.setNomClient("NouveauNom");
        updated.setPrenomClient("NouveauPrenom");
        updated.setEmail("new@mail.com");
        updated.setTelephone("0700000000");
        updated.setMotDePasse("newpass");

        when(utilisateurRepository.findById(1L)).thenReturn(Optional.of(utilisateur));
        when(passwordEncoder.encode("newpass")).thenReturn("encodedNewPass");
        when(utilisateurRepository.save(any(Utilisateur.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Utilisateur result = utilisateurService.updateUser(1L, updated);

        assertEquals("NouveauNom", result.getNomClient());
        assertEquals("NouveauPrenom", result.getPrenomClient());
        assertEquals("new@mail.com", result.getEmail());
        assertEquals("encodedNewPass", result.getMotDePasse());
    }

    @Test
    void testUpdateUser_notFound_shouldThrowException() {
        when(utilisateurRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () ->
                utilisateurService.updateUser(1L, utilisateur));

        assertEquals("Utilisateur non trouvé", ex.getMessage());
    }

    @Test
    void testShowAllUser_shouldReturnList() {
        List<Utilisateur> list = List.of(utilisateur);
        when(utilisateurRepository.findAll()).thenReturn(list);

        List<Utilisateur> result = utilisateurService.showAllUser();
        assertEquals(1, result.size());
    }

    @Test
    void testShowUserById_found() {
        when(utilisateurRepository.findById(1L)).thenReturn(Optional.of(utilisateur));

        Optional<Utilisateur> result = utilisateurService.showUserById(1L);
        assertTrue(result.isPresent());
        assertEquals(utilisateur.getEmail(), result.get().getEmail());
    }

    @Test
    void testShowUserByEmail_found() {
        when(utilisateurRepository.findByEmail("test@example.com")).thenReturn(Optional.of(utilisateur));

        Optional<Utilisateur> result = utilisateurService.showUserByEmail("test@example.com");
        assertTrue(result.isPresent());
        assertEquals(utilisateur.getEmail(), result.get().getEmail());
    }

    @Test
    void testAuthenticate_success() {
        when(utilisateurRepository.findByEmail("test@example.com")).thenReturn(Optional.of(utilisateur));
        when(passwordEncoder.matches("password123", utilisateur.getMotDePasse())).thenReturn(true);

        Utilisateur result = utilisateurService.authenticate("test@example.com", "password123");

        assertEquals(utilisateur.getEmail(), result.getEmail());
    }

    @Test
    void testAuthenticate_wrongPassword_shouldThrow() {
        when(utilisateurRepository.findByEmail("test@example.com")).thenReturn(Optional.of(utilisateur));
        when(passwordEncoder.matches("wrong", utilisateur.getMotDePasse())).thenReturn(false);

        RuntimeException ex = assertThrows(RuntimeException.class, () ->
                utilisateurService.authenticate("test@example.com", "wrong"));

        assertEquals("Email ou mot de passe incorrect", ex.getMessage());
    }

    @Test
    void testAuthenticate_userNotFound_shouldThrow() {
        when(utilisateurRepository.findByEmail("no@mail.com")).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () ->
                utilisateurService.authenticate("no@mail.com", "any"));

        assertEquals("Email ou mot de passe incorrect", ex.getMessage());
    }
}
