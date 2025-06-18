package com.example.demo.contollers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.models.Livreur;
import com.example.demo.services.LivreurService;

@RestController
@RequestMapping("/livreurs")
public class LivreurController {
	@Autowired
	LivreurService livreurService;

	@GetMapping("/all")
	public List<Livreur> getAllLivreurs() {
		return livreurService.showAllLivreurs();
	}

	@GetMapping("/{id}")
	public Optional<Livreur> getLivreurById(@PathVariable Long id) {
		return livreurService.showLivreurById(id);
	}

	@PutMapping("/{id}")
	public Livreur updateLivreur(@PathVariable Long id, @RequestBody Livreur livreur) {
		return livreurService.updateLivreur(id, livreur);
	}

	@PostMapping("/add")
	public Livreur createLivreur(@RequestBody Livreur livreur) {
		return livreurService.createLivreur(livreur);
	}

	@DeleteMapping("/{id}")
	public void deleteLivreur(@PathVariable Long id) {
		livreurService.deleteLivreur(id);
	}
}