package com.exemplo.cofrinhometas.controller;

import com.exemplo.cofrinhometas.model.Meta;
import com.exemplo.cofrinhometas.repository.MetaRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/metas")
public class MetaController {

    private final MetaRepository repository;

    public MetaController(MetaRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Meta> listar() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Meta> buscar(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Meta criar(@RequestBody Meta meta) {
        return repository.save(meta);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Meta> atualizar(@PathVariable Long id, @RequestBody Meta meta) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        meta.setId(id);
        return ResponseEntity.ok(repository.save(meta));
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
