package com.exemplo.igreja.controller;

import com.exemplo.igreja.model.Celula;
import com.exemplo.igreja.repository.CelulaRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/celulas")
public class CelulaController {

    private final CelulaRepository repository;

    public CelulaController(CelulaRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Celula> listar() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Celula> buscar(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Celula criar(@RequestBody Celula celula) {
        return repository.save(celula);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Celula> atualizar(@PathVariable Long id, @RequestBody Celula celula) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        celula.setId(id);
        return ResponseEntity.ok(repository.save(celula));
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
