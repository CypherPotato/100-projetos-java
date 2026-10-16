package com.exemplo.patrimonio.controller;

import com.exemplo.patrimonio.model.Bem;
import com.exemplo.patrimonio.repository.BemRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bens")
public class BemController {

    private final BemRepository repository;

    public BemController(BemRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Bem> listar(@RequestParam(required = false) Long setorId) {
        if (setorId != null) {
            return repository.findBySetorId(setorId);
        }
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Bem> buscar(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Bem criar(@RequestBody Bem bem) {
        return repository.save(bem);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Bem> atualizar(@PathVariable Long id, @RequestBody Bem bem) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        bem.setId(id);
        return ResponseEntity.ok(repository.save(bem));
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
