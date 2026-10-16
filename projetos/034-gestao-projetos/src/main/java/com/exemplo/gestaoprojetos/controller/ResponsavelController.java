package com.exemplo.gestaoprojetos.controller;

import com.exemplo.gestaoprojetos.model.Responsavel;
import com.exemplo.gestaoprojetos.repository.ResponsavelRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/responsaveis")
public class ResponsavelController {

    private final ResponsavelRepository repository;

    public ResponsavelController(ResponsavelRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Responsavel> listar() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Responsavel> buscar(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Responsavel criar(@RequestBody Responsavel responsavel) {
        return repository.save(responsavel);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Responsavel> atualizar(@PathVariable Long id, @RequestBody Responsavel responsavel) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        responsavel.setId(id);
        return ResponseEntity.ok(repository.save(responsavel));
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
