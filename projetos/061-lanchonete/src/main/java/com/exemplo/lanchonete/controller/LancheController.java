package com.exemplo.lanchonete.controller;

import com.exemplo.lanchonete.model.Lanche;
import com.exemplo.lanchonete.repository.LancheRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/lanches")
public class LancheController {

    private final LancheRepository repository;

    public LancheController(LancheRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Lanche> listar(@RequestParam(required = false) Long categoriaId) {
        if (categoriaId != null) {
            return repository.findByCategoriaId(categoriaId);
        }
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Lanche> buscar(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Lanche criar(@RequestBody Lanche lanche) {
        return repository.save(lanche);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Lanche> atualizar(@PathVariable Long id, @RequestBody Lanche lanche) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        lanche.setId(id);
        return ResponseEntity.ok(repository.save(lanche));
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
