package com.exemplo.receitas.controller;

import com.exemplo.receitas.model.Receita;
import com.exemplo.receitas.repository.ReceitaRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/receitas")
public class ReceitaController {

    private final ReceitaRepository repository;

    public ReceitaController(ReceitaRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Receita> listar() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Receita> buscar(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Receita criar(@RequestBody Receita receita) {
        return repository.save(receita);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Receita> atualizar(@PathVariable Long id, @RequestBody Receita receita) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        receita.setId(id);
        return ResponseEntity.ok(repository.save(receita));
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
