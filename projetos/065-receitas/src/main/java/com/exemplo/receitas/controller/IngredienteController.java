package com.exemplo.receitas.controller;

import com.exemplo.receitas.model.Ingrediente;
import com.exemplo.receitas.repository.IngredienteRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ingredientes")
public class IngredienteController {

    private final IngredienteRepository repository;

    public IngredienteController(IngredienteRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Ingrediente> listar(@RequestParam(required = false) Long receitaId) {
        if (receitaId != null) {
            return repository.findByReceitaId(receitaId);
        }
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ingrediente> buscar(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Ingrediente criar(@RequestBody Ingrediente ingrediente) {
        return repository.save(ingrediente);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Ingrediente> atualizar(@PathVariable Long id, @RequestBody Ingrediente ingrediente) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        ingrediente.setId(id);
        return ResponseEntity.ok(repository.save(ingrediente));
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
