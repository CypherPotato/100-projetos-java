package com.exemplo.cartoescredito.controller;

import com.exemplo.cartoescredito.model.Compra;
import com.exemplo.cartoescredito.repository.CompraRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/compras")
public class CompraController {

    private final CompraRepository repository;

    public CompraController(CompraRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Compra> listar(@RequestParam(required = false) Long faturaId) {
        if (faturaId != null) {
            return repository.findByFaturaId(faturaId);
        }
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Compra> buscar(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Compra criar(@RequestBody Compra compra) {
        return repository.save(compra);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Compra> atualizar(@PathVariable Long id, @RequestBody Compra compra) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        compra.setId(id);
        return ResponseEntity.ok(repository.save(compra));
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
