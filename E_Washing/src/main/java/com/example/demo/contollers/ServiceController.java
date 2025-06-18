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

import com.example.demo.models.Services;
import com.example.demo.services.ServiceService;

@RestController
@RequestMapping("/services")
public class ServiceController {
	@Autowired
	ServiceService serviceService;

	@GetMapping
	public List<Services> getAllServices() {
		return serviceService.showAllServices();
	}

	@GetMapping("/{id}")
	public Optional<Services> getServiceById(@PathVariable Long id) {
		return serviceService.showServiceById(id);
	}

	@PutMapping("/{id}")
	public Services updateService(@PathVariable Long id, @RequestBody Services service) {
		return serviceService.updateService(id, service);
	}

	@PostMapping("/add")
	public Services createService(@RequestBody Services service) {
		return serviceService.createService(service);
	}

	@DeleteMapping("/{id}")
	public void deleteService(@PathVariable Long id) {
		serviceService.deleteService(id);
	}
}