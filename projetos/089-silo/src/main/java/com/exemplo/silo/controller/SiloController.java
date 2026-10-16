package com.exemplo.silo.controller;

import com.exemplo.silo.model.Silo;
import com.exemplo.silo.repository.SiloRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/silos")
public class SiloController {

    private final SiloRepository repository;

    public SiloController(SiloRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Silo> listar() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Silo> buscar(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Silo criar(@RequestBody Silo silo) {
        return repository.save(silo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Silo> atualizar(@PathVariable Long id, @RequestBody Silo silo) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        silo.setId(id);
        return ResponseEntity.ok(repository.save(silo));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
