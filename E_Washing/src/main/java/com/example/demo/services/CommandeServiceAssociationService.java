package com.example.demo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.models.CommandeService;
import com.example.demo.repositories.CommandeServiceRepository;

@Service
public class CommandeServiceAssociationService {
	@Autowired
	CommandeServiceRepository commandeServiceRepository;

	public List<CommandeService> showAllCommandeServices() {
		return commandeServiceRepository.findAll();
	}

	public Optional<CommandeService> showCommandeServiceById(Long id) {
		return commandeServiceRepository.findById(id);
	}

	public CommandeService createCommandeService(CommandeService commandeService) {
		return commandeServiceRepository.save(commandeService);
	}

	public void deleteCommandeService(Long id) {
		commandeServiceRepository.deleteById(id);
	}
}
