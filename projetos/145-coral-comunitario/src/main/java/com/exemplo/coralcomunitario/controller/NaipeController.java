package com.exemplo.coralcomunitario.controller;

import com.exemplo.coralcomunitario.model.Naipe;
import com.exemplo.coralcomunitario.repository.NaipeRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/naipes")
public class NaipeController {

    private final NaipeRepository repository;

    public NaipeController(NaipeRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Naipe> listar() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Naipe> buscar(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Naipe criar(@RequestBody Naipe naipe) {
        return repository.save(naipe);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Naipe> atualizar(@PathVariable Long id, @RequestBody Naipe naipe) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        naipe.setId(id);
        return ResponseEntity.ok(repository.save(naipe));
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
