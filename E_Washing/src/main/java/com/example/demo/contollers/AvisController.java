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

import com.example.demo.models.Avis;
import com.example.demo.services.AvisService;

@RestController
@RequestMapping("/avis")
public class AvisController {
	@Autowired
	AvisService avisService;

	@GetMapping("/all")
	public List<Avis> getAllAvis() {
		return avisService.showAllAvis();
	}

	@GetMapping("/{id}")
	public Optional<Avis> getAvisById(@PathVariable Long id) {
		return avisService.showAvisById(id);
	}

	@PutMapping("/update/{id}")
	public Avis updateAvis(@PathVariable Long id, @RequestBody Avis avis) {
		return avisService.updateAvis(id, avis);
	}

	@PostMapping("/add")
	public Avis createAvis(@RequestBody Avis avis) {
		return avisService.createAvis(avis);
	}

	@DeleteMapping("/{id}")
	public void deleteAvis(@PathVariable Long id) {
		avisService.deleteAvis(id);
	}
}
