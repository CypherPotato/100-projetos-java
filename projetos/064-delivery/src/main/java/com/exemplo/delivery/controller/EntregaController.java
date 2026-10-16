package com.exemplo.delivery.controller;

import com.exemplo.delivery.model.Entrega;
import com.exemplo.delivery.repository.EntregaRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/entregas")
public class EntregaController {

    private final EntregaRepository repository;

    public EntregaController(EntregaRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Entrega> listar(@RequestParam(required = false) Long restauranteId, @RequestParam(required = false) Long entregadorId) {
        if (restauranteId != null) {
            return repository.findByRestauranteId(restauranteId);
        }
        if (entregadorId != null) {
            return repository.findByEntregadorId(entregadorId);
        }
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Entrega> buscar(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Entrega criar(@RequestBody Entrega entrega) {
        return repository.save(entrega);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Entrega> atualizar(@PathVariable Long id, @RequestBody Entrega entrega) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        entrega.setId(id);
        return ResponseEntity.ok(repository.save(entrega));
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
