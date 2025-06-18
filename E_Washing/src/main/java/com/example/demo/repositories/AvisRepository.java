package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.models.Avis;

public interface AvisRepository extends JpaRepository<Avis, Long> {

}
