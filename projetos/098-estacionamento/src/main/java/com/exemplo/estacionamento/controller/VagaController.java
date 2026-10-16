package com.exemplo.estacionamento.controller;

import com.exemplo.estacionamento.model.Vaga;
import com.exemplo.estacionamento.repository.VagaRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/vagas")
public class VagaController {

    private final VagaRepository repository;

    public VagaController(VagaRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Vaga> listar() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Vaga> buscar(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Vaga criar(@RequestBody Vaga vaga) {
        return repository.save(vaga);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Vaga> atualizar(@PathVariable Long id, @RequestBody Vaga vaga) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        vaga.setId(id);
        return ResponseEntity.ok(repository.save(vaga));
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
