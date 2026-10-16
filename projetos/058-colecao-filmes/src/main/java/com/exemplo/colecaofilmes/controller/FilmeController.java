package com.exemplo.colecaofilmes.controller;

import com.exemplo.colecaofilmes.model.Filme;
import com.exemplo.colecaofilmes.repository.FilmeRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/filmes")
public class FilmeController {

    private final FilmeRepository repository;

    public FilmeController(FilmeRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Filme> listar(@RequestParam(required = false) Long diretorId) {
        if (diretorId != null) {
            return repository.findByDiretorId(diretorId);
        }
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Filme> buscar(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Filme criar(@RequestBody Filme filme) {
        return repository.save(filme);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Filme> atualizar(@PathVariable Long id, @RequestBody Filme filme) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        filme.setId(id);
        return ResponseEntity.ok(repository.save(filme));
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
