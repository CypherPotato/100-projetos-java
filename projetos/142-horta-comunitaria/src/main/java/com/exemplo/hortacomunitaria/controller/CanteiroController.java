package com.exemplo.hortacomunitaria.controller;

import com.exemplo.hortacomunitaria.model.Canteiro;
import com.exemplo.hortacomunitaria.repository.CanteiroRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/canteiros")
public class CanteiroController {

    private final CanteiroRepository repository;

    public CanteiroController(CanteiroRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Canteiro> listar(@RequestParam(required = false) Long moradorId) {
        if (moradorId != null) {
            return repository.findByMoradorId(moradorId);
        }
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Canteiro> buscar(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Canteiro criar(@RequestBody Canteiro canteiro) {
        return repository.save(canteiro);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Canteiro> atualizar(@PathVariable Long id, @RequestBody Canteiro canteiro) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        canteiro.setId(id);
        return ResponseEntity.ok(repository.save(canteiro));
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
