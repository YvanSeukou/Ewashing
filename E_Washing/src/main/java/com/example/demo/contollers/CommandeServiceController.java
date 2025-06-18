package com.example.demo.contollers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.models.CommandeService;
import com.example.demo.services.CommandeServiceAssociationService;

@RestController
@RequestMapping("/commande-services")
public class CommandeServiceController {
	@Autowired
	CommandeServiceAssociationService commandeServiceAssociationService;

	@GetMapping
	public List<CommandeService> getAllCommandeServices() {
		return commandeServiceAssociationService.showAllCommandeServices();
	}

	@GetMapping("/{id}")
	public Optional<CommandeService> getCommandeServiceById(@PathVariable Long id) {
		return commandeServiceAssociationService.showCommandeServiceById(id);
	}

	@PostMapping("/add")
	public CommandeService createCommandeService(@RequestBody CommandeService commandeService) {
		return commandeServiceAssociationService.createCommandeService(commandeService);
	}

	@DeleteMapping("/{id}")
	public void deleteCommandeService(@PathVariable Long id) {
		commandeServiceAssociationService.deleteCommandeService(id);
	}
}