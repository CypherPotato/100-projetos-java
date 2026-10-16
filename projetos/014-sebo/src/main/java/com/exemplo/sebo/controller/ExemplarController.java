package com.exemplo.sebo.controller;

import com.exemplo.sebo.model.Exemplar;
import com.exemplo.sebo.repository.ExemplarRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/exemplares")
public class ExemplarController {

    private final ExemplarRepository repository;

    public ExemplarController(ExemplarRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Exemplar> listar(@RequestParam(required = false) Long generoId) {
        if (generoId != null) {
            return repository.findByGeneroId(generoId);
        }
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Exemplar> buscar(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Exemplar criar(@RequestBody Exemplar exemplar) {
        return repository.save(exemplar);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Exemplar> atualizar(@PathVariable Long id, @RequestBody Exemplar exemplar) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        exemplar.setId(id);
        return ResponseEntity.ok(repository.save(exemplar));
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
