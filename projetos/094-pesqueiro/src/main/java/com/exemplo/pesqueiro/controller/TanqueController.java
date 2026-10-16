package com.exemplo.pesqueiro.controller;

import com.exemplo.pesqueiro.model.Tanque;
import com.exemplo.pesqueiro.repository.TanqueRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tanques")
public class TanqueController {

    private final TanqueRepository repository;

    public TanqueController(TanqueRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Tanque> listar() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tanque> buscar(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Tanque criar(@RequestBody Tanque tanque) {
        return repository.save(tanque);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tanque> atualizar(@PathVariable Long id, @RequestBody Tanque tanque) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        tanque.setId(id);
        return ResponseEntity.ok(repository.save(tanque));
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
