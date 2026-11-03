package com.exemplo.coralcomunitario.controller;

import com.exemplo.coralcomunitario.model.Corista;
import com.exemplo.coralcomunitario.repository.CoristaRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/coristas")
public class CoristaController {

    private final CoristaRepository repository;

    public CoristaController(CoristaRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Corista> listar(@RequestParam(required = false) Long naipeId) {
        if (naipeId != null) {
            return repository.findByNaipeId(naipeId);
        }
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Corista> buscar(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Corista criar(@RequestBody Corista corista) {
        return repository.save(corista);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Corista> atualizar(@PathVariable Long id, @RequestBody Corista corista) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        corista.setId(id);
        return ResponseEntity.ok(repository.save(corista));
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
