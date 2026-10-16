package com.exemplo.coopagricola.controller;

import com.exemplo.coopagricola.model.Agricultor;
import com.exemplo.coopagricola.repository.AgricultorRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/agricultores")
public class AgricultorController {

    private final AgricultorRepository repository;

    public AgricultorController(AgricultorRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Agricultor> listar() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Agricultor> buscar(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Agricultor criar(@RequestBody Agricultor agricultor) {
        return repository.save(agricultor);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Agricultor> atualizar(@PathVariable Long id, @RequestBody Agricultor agricultor) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        agricultor.setId(id);
        return ResponseEntity.ok(repository.save(agricultor));
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
