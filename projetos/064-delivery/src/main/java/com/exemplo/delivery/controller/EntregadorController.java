package com.exemplo.delivery.controller;

import com.exemplo.delivery.model.Entregador;
import com.exemplo.delivery.repository.EntregadorRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/entregadores")
public class EntregadorController {

    private final EntregadorRepository repository;

    public EntregadorController(EntregadorRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Entregador> listar() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Entregador> buscar(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Entregador criar(@RequestBody Entregador entregador) {
        return repository.save(entregador);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Entregador> atualizar(@PathVariable Long id, @RequestBody Entregador entregador) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        entregador.setId(id);
        return ResponseEntity.ok(repository.save(entregador));
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
