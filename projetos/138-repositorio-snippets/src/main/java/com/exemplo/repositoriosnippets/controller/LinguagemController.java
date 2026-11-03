package com.exemplo.repositoriosnippets.controller;

import com.exemplo.repositoriosnippets.model.Linguagem;
import com.exemplo.repositoriosnippets.repository.LinguagemRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/linguagens")
public class LinguagemController {

    private final LinguagemRepository repository;

    public LinguagemController(LinguagemRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Linguagem> listar() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Linguagem> buscar(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Linguagem criar(@RequestBody Linguagem linguagem) {
        return repository.save(linguagem);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Linguagem> atualizar(@PathVariable Long id, @RequestBody Linguagem linguagem) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        linguagem.setId(id);
        return ResponseEntity.ok(repository.save(linguagem));
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
