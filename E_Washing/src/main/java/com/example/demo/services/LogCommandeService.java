package com.example.demo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.documents.LogCommande;
import com.example.demo.repositories.LogCommandeRepository;

@Service
public class LogCommandeService {

    @Autowired
    private LogCommandeRepository logCommandeRepository;

    public List<LogCommande> findAllLogs() {
        return logCommandeRepository.findAll();
    }

    public Optional<LogCommande> findLogById(String id) {
        return logCommandeRepository.findById(id);
    }

    public List<LogCommande> findLogsByCommande(String idCommande) {
        return logCommandeRepository.findByIdCommande(idCommande);
    }

    public LogCommande saveLog(LogCommande log) {
        return logCommandeRepository.save(log);
    }

    public LogCommande updateLogCommande(String id, LogCommande newLog) {
        return logCommandeRepository.findById(id).map(existingLog -> {
            existingLog.setNom(newLog.getNom());
            existingLog.setAction(newLog.getAction());
            existingLog.setDateAction(newLog.getDateAction());
            existingLog.setIdCommande(newLog.getIdCommande());
            return logCommandeRepository.save(existingLog);
        }).orElse(null);
    }
}
