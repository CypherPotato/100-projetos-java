package com.exemplo.apicultura.controller;

import com.exemplo.apicultura.model.Apiario;
import com.exemplo.apicultura.repository.ApiarioRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/apiarios")
public class ApiarioController {

    private final ApiarioRepository repository;

    public ApiarioController(ApiarioRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Apiario> listar() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Apiario> buscar(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Apiario criar(@RequestBody Apiario apiario) {
        return repository.save(apiario);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Apiario> atualizar(@PathVariable Long id, @RequestBody Apiario apiario) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        apiario.setId(id);
        return ResponseEntity.ok(repository.save(apiario));
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
