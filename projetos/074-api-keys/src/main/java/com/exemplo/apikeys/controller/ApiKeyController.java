package com.exemplo.apikeys.controller;

import com.exemplo.apikeys.model.ApiKey;
import com.exemplo.apikeys.repository.ApiKeyRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api-keys")
public class ApiKeyController {

    private final ApiKeyRepository repository;

    public ApiKeyController(ApiKeyRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<ApiKey> listar(@RequestParam(required = false) Long clienteId) {
        if (clienteId != null) {
            return repository.findByClienteId(clienteId);
        }
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiKey> buscar(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiKey criar(@RequestBody ApiKey apiKey) {
        return repository.save(apiKey);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiKey> atualizar(@PathVariable Long id, @RequestBody ApiKey apiKey) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        apiKey.setId(id);
        return ResponseEntity.ok(repository.save(apiKey));
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
