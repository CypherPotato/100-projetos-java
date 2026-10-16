package com.exemplo.livraria.controller;

import com.exemplo.livraria.model.Editora;
import com.exemplo.livraria.repository.EditoraRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/editoras")
public class EditoraController {

    private final EditoraRepository repository;

    public EditoraController(EditoraRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Editora> listar() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Editora> buscar(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Editora criar(@RequestBody Editora editora) {
        return repository.save(editora);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Editora> atualizar(@PathVariable Long id, @RequestBody Editora editora) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        editora.setId(id);
        return ResponseEntity.ok(repository.save(editora));
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
