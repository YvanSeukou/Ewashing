package com.example.demo.contollers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.documents.LogCommande;
import com.example.demo.services.LogCommandeService;

@RestController
@RequestMapping("/api/logs")
public class LogCommandeController {

    @Autowired
    private LogCommandeService logCommandeService;

    @GetMapping("/all")
    public List<LogCommande> getAllLogs() {
        return logCommandeService.findAllLogs();
    }

    @GetMapping("/{id}")
    public ResponseEntity<LogCommande> getLogById(@PathVariable String id) {
        Optional<LogCommande> log = logCommandeService.findLogById(id);
        return log.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/commande/{idCommande}")
    public List<LogCommande> getLogsByCommande(@PathVariable String idCommande) {
        return logCommandeService.findLogsByCommande(idCommande);
    }

    @PostMapping
    public LogCommande createLog(@RequestBody LogCommande log) {
        return logCommandeService.saveLog(log);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<LogCommande> updateLog(@PathVariable String id, @RequestBody LogCommande logCommande) {
        LogCommande updated = logCommandeService.updateLogCommande(id, logCommande);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }
}
