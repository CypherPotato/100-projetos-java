package com.exemplo.controleassinaturas.controller;

import com.exemplo.controleassinaturas.model.Assinatura;
import com.exemplo.controleassinaturas.repository.AssinaturaRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/assinaturas")
public class AssinaturaController {

    private final AssinaturaRepository repository;

    public AssinaturaController(AssinaturaRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Assinatura> listar(@RequestParam(required = false) Long categoriaId) {
        if (categoriaId != null) {
            return repository.findByCategoriaId(categoriaId);
        }
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Assinatura> buscar(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Assinatura criar(@RequestBody Assinatura assinatura) {
        return repository.save(assinatura);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Assinatura> atualizar(@PathVariable Long id, @RequestBody Assinatura assinatura) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        assinatura.setId(id);
        return ResponseEntity.ok(repository.save(assinatura));
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
