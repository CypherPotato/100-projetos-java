package com.exemplo.pesqueiro.controller;

import com.exemplo.pesqueiro.model.Despesca;
import com.exemplo.pesqueiro.repository.DespescaRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/despescas")
public class DespescaController {

    private final DespescaRepository repository;

    public DespescaController(DespescaRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Despesca> listar(@RequestParam(required = false) Long tanqueId) {
        if (tanqueId != null) {
            return repository.findByTanqueId(tanqueId);
        }
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Despesca> buscar(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Despesca criar(@RequestBody Despesca despesca) {
        return repository.save(despesca);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Despesca> atualizar(@PathVariable Long id, @RequestBody Despesca despesca) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        despesca.setId(id);
        return ResponseEntity.ok(repository.save(despesca));
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
