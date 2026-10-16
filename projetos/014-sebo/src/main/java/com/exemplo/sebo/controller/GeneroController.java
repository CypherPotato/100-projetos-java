package com.exemplo.sebo.controller;

import com.exemplo.sebo.model.Genero;
import com.exemplo.sebo.repository.GeneroRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/generos")
public class GeneroController {

    private final GeneroRepository repository;

    public GeneroController(GeneroRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Genero> listar() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Genero> buscar(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Genero criar(@RequestBody Genero genero) {
        return repository.save(genero);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Genero> atualizar(@PathVariable Long id, @RequestBody Genero genero) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        genero.setId(id);
        return ResponseEntity.ok(repository.save(genero));
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
