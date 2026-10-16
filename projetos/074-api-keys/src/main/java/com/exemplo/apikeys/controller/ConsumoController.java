package com.exemplo.apikeys.controller;

import com.exemplo.apikeys.model.Consumo;
import com.exemplo.apikeys.repository.ConsumoRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/consumos")
public class ConsumoController {

    private final ConsumoRepository repository;

    public ConsumoController(ConsumoRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Consumo> listar(@RequestParam(required = false) Long apiKeyId) {
        if (apiKeyId != null) {
            return repository.findByApiKeyId(apiKeyId);
        }
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Consumo> buscar(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Consumo criar(@RequestBody Consumo consumo) {
        return repository.save(consumo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Consumo> atualizar(@PathVariable Long id, @RequestBody Consumo consumo) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        consumo.setId(id);
        return ResponseEntity.ok(repository.save(consumo));
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
