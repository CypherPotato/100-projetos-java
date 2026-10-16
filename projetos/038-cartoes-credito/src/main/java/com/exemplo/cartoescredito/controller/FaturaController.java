package com.exemplo.cartoescredito.controller;

import com.exemplo.cartoescredito.model.Fatura;
import com.exemplo.cartoescredito.repository.FaturaRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/faturas")
public class FaturaController {

    private final FaturaRepository repository;

    public FaturaController(FaturaRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Fatura> listar(@RequestParam(required = false) Long cartaoId) {
        if (cartaoId != null) {
            return repository.findByCartaoId(cartaoId);
        }
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Fatura> buscar(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Fatura criar(@RequestBody Fatura fatura) {
        return repository.save(fatura);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Fatura> atualizar(@PathVariable Long id, @RequestBody Fatura fatura) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        fatura.setId(id);
        return ResponseEntity.ok(repository.save(fatura));
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
