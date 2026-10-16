package com.exemplo.condominio.controller;

import com.exemplo.condominio.model.Apartamento;
import com.exemplo.condominio.repository.ApartamentoRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/apartamentos")
public class ApartamentoController {

    private final ApartamentoRepository repository;

    public ApartamentoController(ApartamentoRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Apartamento> listar() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Apartamento> buscar(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Apartamento criar(@RequestBody Apartamento apartamento) {
        return repository.save(apartamento);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Apartamento> atualizar(@PathVariable Long id, @RequestBody Apartamento apartamento) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        apartamento.setId(id);
        return ResponseEntity.ok(repository.save(apartamento));
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
