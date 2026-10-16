package com.exemplo.investimentos.controller;

import com.exemplo.investimentos.model.Ativo;
import com.exemplo.investimentos.repository.AtivoRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ativos")
public class AtivoController {

    private final AtivoRepository repository;

    public AtivoController(AtivoRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Ativo> listar(@RequestParam(required = false) Long carteiraId) {
        if (carteiraId != null) {
            return repository.findByCarteiraId(carteiraId);
        }
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ativo> buscar(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Ativo criar(@RequestBody Ativo ativo) {
        return repository.save(ativo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Ativo> atualizar(@PathVariable Long id, @RequestBody Ativo ativo) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        ativo.setId(id);
        return ResponseEntity.ok(repository.save(ativo));
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
