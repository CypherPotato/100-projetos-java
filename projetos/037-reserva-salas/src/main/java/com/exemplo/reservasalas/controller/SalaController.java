package com.exemplo.reservasalas.controller;

import com.exemplo.reservasalas.model.Sala;
import com.exemplo.reservasalas.repository.SalaRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/salas")
public class SalaController {

    private final SalaRepository repository;

    public SalaController(SalaRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Sala> listar() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Sala> buscar(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Sala criar(@RequestBody Sala sala) {
        return repository.save(sala);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Sala> atualizar(@PathVariable Long id, @RequestBody Sala sala) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        sala.setId(id);
        return ResponseEntity.ok(repository.save(sala));
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
