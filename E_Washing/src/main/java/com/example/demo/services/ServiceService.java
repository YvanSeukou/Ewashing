package com.example.demo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.models.Services;
import com.example.demo.repositories.ServiceRepository;

@Service
public class ServiceService {
	@Autowired
	ServiceRepository serviceRepository;

	public List<Services> showAllServices() {
		return serviceRepository.findAll();
	}

	public Optional<Services> showServiceById(Long id) {
		return serviceRepository.findById(id);
	}

	public Services updateService(Long id, Services newService) {
		return serviceRepository.findById(id).map(service -> {
			service.setTypeService(newService.getTypeService());
			service.setTarifService(newService.getTarifService());
			return serviceRepository.save(service);
		}).orElse(null);
	}

	public Services createService(Services service) {
		return serviceRepository.save(service);
	}

	public void deleteService(Long id) {
		serviceRepository.deleteById(id);
	}
}
