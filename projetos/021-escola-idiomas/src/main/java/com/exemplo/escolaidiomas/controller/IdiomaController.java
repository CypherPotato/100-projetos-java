package com.exemplo.escolaidiomas.controller;

import com.exemplo.escolaidiomas.model.Idioma;
import com.exemplo.escolaidiomas.repository.IdiomaRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/idiomas")
public class IdiomaController {

    private final IdiomaRepository repository;

    public IdiomaController(IdiomaRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Idioma> listar() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Idioma> buscar(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Idioma criar(@RequestBody Idioma idioma) {
        return repository.save(idioma);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Idioma> atualizar(@PathVariable Long id, @RequestBody Idioma idioma) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        idioma.setId(id);
        return ResponseEntity.ok(repository.save(idioma));
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
