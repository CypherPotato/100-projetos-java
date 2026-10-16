package com.exemplo.restaurante.controller;

import com.exemplo.restaurante.model.Mesa;
import com.exemplo.restaurante.repository.MesaRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mesas")
public class MesaController {

    private final MesaRepository repository;

    public MesaController(MesaRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Mesa> listar() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Mesa> buscar(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mesa criar(@RequestBody Mesa mesa) {
        return repository.save(mesa);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Mesa> atualizar(@PathVariable Long id, @RequestBody Mesa mesa) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        mesa.setId(id);
        return ResponseEntity.ok(repository.save(mesa));
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
