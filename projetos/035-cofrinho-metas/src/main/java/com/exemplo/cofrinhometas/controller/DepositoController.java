package com.exemplo.cofrinhometas.controller;

import com.exemplo.cofrinhometas.model.Deposito;
import com.exemplo.cofrinhometas.repository.DepositoRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/depositos")
public class DepositoController {

    private final DepositoRepository repository;

    public DepositoController(DepositoRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Deposito> listar(@RequestParam(required = false) Long metaId) {
        if (metaId != null) {
            return repository.findByMetaId(metaId);
        }
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Deposito> buscar(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Deposito criar(@RequestBody Deposito deposito) {
        return repository.save(deposito);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Deposito> atualizar(@PathVariable Long id, @RequestBody Deposito deposito) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        deposito.setId(id);
        return ResponseEntity.ok(repository.save(deposito));
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
