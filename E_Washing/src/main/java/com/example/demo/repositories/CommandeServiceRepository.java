package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.models.CommandeService;

public interface CommandeServiceRepository extends JpaRepository<CommandeService, Long> {

}
