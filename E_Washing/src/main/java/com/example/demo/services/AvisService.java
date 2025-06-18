package com.example.demo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.models.Avis;
import com.example.demo.repositories.AvisRepository;

@Service
public class AvisService {
	@Autowired
	AvisRepository avisRepository;

	public List<Avis> showAllAvis() {
		return avisRepository.findAll();
	}

	public Optional<Avis> showAvisById(Long id) {
		return avisRepository.findById(id);
	}

	public Avis updateAvis(Long id, Avis newAvis) {
		return avisRepository.findById(id).map(avis -> {
			avis.setContenu(newAvis.getContenu());
			avis.setDateAvis(newAvis.getDateAvis());
			return avisRepository.save(avis);
		}).orElse(null);
	}

	public Avis createAvis(Avis avis) {
		return avisRepository.save(avis);
	}

	public void deleteAvis(Long id) {
		avisRepository.deleteById(id);
	}
}
