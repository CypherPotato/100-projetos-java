package com.exemplo.repositoriosnippets.controller;

import com.exemplo.repositoriosnippets.model.Snippet;
import com.exemplo.repositoriosnippets.repository.SnippetRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/snippets")
public class SnippetController {

    private final SnippetRepository repository;

    public SnippetController(SnippetRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Snippet> listar(@RequestParam(required = false) Long linguagemId) {
        if (linguagemId != null) {
            return repository.findByLinguagemId(linguagemId);
        }
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Snippet> buscar(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Snippet criar(@RequestBody Snippet snippet) {
        return repository.save(snippet);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Snippet> atualizar(@PathVariable Long id, @RequestBody Snippet snippet) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        snippet.setId(id);
        return ResponseEntity.ok(repository.save(snippet));
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
