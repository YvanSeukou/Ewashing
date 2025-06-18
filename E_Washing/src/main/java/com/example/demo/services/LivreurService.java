package com.example.demo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.models.Livreur;
import com.example.demo.repositories.LivreurRepository;

@Service
public class LivreurService {
	@Autowired
	LivreurRepository livreurRepository;

	public List<Livreur> showAllLivreurs() {
		return livreurRepository.findAll();
	}

	public Optional<Livreur> showLivreurById(Long id) {
		return livreurRepository.findById(id);
	}

	public Livreur updateLivreur(Long id, Livreur newLivreur) {
		return livreurRepository.findById(id).map(livreur -> {
			livreur.setNomLivreur(newLivreur.getNomLivreur());
			livreur.setNumeroLivreur(newLivreur.getNumeroLivreur());
			return livreurRepository.save(livreur);
		}).orElse(null);
	}

	public Livreur createLivreur(Livreur livreur) {
		return livreurRepository.save(livreur);
	}

	public void deleteLivreur(Long id) {
		livreurRepository.deleteById(id);
	}
}
