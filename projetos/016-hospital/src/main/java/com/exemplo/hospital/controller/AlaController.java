package com.exemplo.hospital.controller;

import com.exemplo.hospital.model.Ala;
import com.exemplo.hospital.repository.AlaRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/alas")
public class AlaController {

    private final AlaRepository repository;

    public AlaController(AlaRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Ala> listar() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ala> buscar(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Ala criar(@RequestBody Ala ala) {
        return repository.save(ala);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Ala> atualizar(@PathVariable Long id, @RequestBody Ala ala) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        ala.setId(id);
        return ResponseEntity.ok(repository.save(ala));
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
