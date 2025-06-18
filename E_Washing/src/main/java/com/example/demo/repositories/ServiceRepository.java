package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.models.Services;

public interface ServiceRepository extends JpaRepository<Services, Long> {

}
