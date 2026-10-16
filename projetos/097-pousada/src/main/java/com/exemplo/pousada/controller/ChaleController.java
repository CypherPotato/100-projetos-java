package com.exemplo.pousada.controller;

import com.exemplo.pousada.model.Chale;
import com.exemplo.pousada.repository.ChaleRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/chales")
public class ChaleController {

    private final ChaleRepository repository;

    public ChaleController(ChaleRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Chale> listar() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Chale> buscar(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Chale criar(@RequestBody Chale chale) {
        return repository.save(chale);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Chale> atualizar(@PathVariable Long id, @RequestBody Chale chale) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        chale.setId(id);
        return ResponseEntity.ok(repository.save(chale));
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
