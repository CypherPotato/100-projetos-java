package com.exemplo.cafeteria.controller;

import com.exemplo.cafeteria.model.GraoCafe;
import com.exemplo.cafeteria.repository.GraoCafeRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/graos-cafe")
public class GraoCafeController {

    private final GraoCafeRepository repository;

    public GraoCafeController(GraoCafeRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<GraoCafe> listar(@RequestParam(required = false) Long fornecedorId) {
        if (fornecedorId != null) {
            return repository.findByFornecedorId(fornecedorId);
        }
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<GraoCafe> buscar(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GraoCafe criar(@RequestBody GraoCafe graoCafe) {
        return repository.save(graoCafe);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GraoCafe> atualizar(@PathVariable Long id, @RequestBody GraoCafe graoCafe) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        graoCafe.setId(id);
        return ResponseEntity.ok(repository.save(graoCafe));
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
