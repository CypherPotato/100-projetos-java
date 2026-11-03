package com.exemplo.casacambio.controller;

import com.exemplo.casacambio.model.Moeda;
import com.exemplo.casacambio.repository.MoedaRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/moedas")
public class MoedaController {

    private final MoedaRepository repository;

    public MoedaController(MoedaRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Moeda> listar() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Moeda> buscar(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Moeda criar(@RequestBody Moeda moeda) {
        return repository.save(moeda);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Moeda> atualizar(@PathVariable Long id, @RequestBody Moeda moeda) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        moeda.setId(id);
        return ResponseEntity.ok(repository.save(moeda));
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
