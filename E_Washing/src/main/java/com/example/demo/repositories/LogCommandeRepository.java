package com.example.demo.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.demo.documents.LogCommande;

public interface LogCommandeRepository extends MongoRepository<LogCommande, String> {
    List<LogCommande> findByIdCommande(String idCommande); // idCommande est un String dans le document
}
