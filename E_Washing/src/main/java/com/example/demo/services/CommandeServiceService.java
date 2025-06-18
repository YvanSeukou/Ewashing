package com.example.demo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.models.Commande;
import com.example.demo.models.CommandeService;
import com.example.demo.repositories.CommandeRepository;

@Service
public class CommandeServiceService {

    @Autowired
    private CommandeRepository commandeRepository;

    public List<Commande> showAllCommandes() {
        return commandeRepository.findAll();
    }

    public Optional<Commande> showCommandeById(Long id) {
        return commandeRepository.findById(id);
    }

    public Commande updateCommande(Long id, Commande newCommande) {
        return commandeRepository.findById(id).map(commande -> {
            commande.setNumeroCommande(newCommande.getNumeroCommande());
            commande.setDateCommande(newCommande.getDateCommande());
            commande.setDateCollecte(newCommande.getDateCollecte());
            commande.setDateLivraison(newCommande.getDateLivraison());
            commande.setStatut(newCommande.getStatut());
            commande.setMontantTotal(newCommande.getMontantTotal());
            commande.setAdresseLivraison(newCommande.getAdresseLivraison());
            commande.setInstructions(newCommande.getInstructions());

            if (newCommande.getCommandeServices() != null) {
                newCommande.getCommandeServices().forEach(cs -> cs.setCommande(commande));
                commande.setCommandeServices(newCommande.getCommandeServices());
            }

            return commandeRepository.save(commande);
        }).orElse(null);
    }

    public Commande createCommande(Commande commande) {
        if (commande.getCommandeServices() != null) {
            commande.getCommandeServices().forEach(cs -> cs.setCommande(commande));
        }
        return commandeRepository.save(commande);
    }

    public void deleteCommande(Long id) {
        commandeRepository.deleteById(id);
    }

    public List<Commande> findCommandesByClient(Long idClient) {
        return commandeRepository.findByClientIdUtilisateur(idClient);
    }
}
