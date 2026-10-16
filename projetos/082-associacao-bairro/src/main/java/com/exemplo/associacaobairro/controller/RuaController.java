package com.exemplo.associacaobairro.controller;

import com.exemplo.associacaobairro.model.Rua;
import com.exemplo.associacaobairro.repository.RuaRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ruas")
public class RuaController {

    private final RuaRepository repository;

    public RuaController(RuaRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Rua> listar() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Rua> buscar(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Rua criar(@RequestBody Rua rua) {
        return repository.save(rua);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Rua> atualizar(@PathVariable Long id, @RequestBody Rua rua) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        rua.setId(id);
        return ResponseEntity.ok(repository.save(rua));
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
