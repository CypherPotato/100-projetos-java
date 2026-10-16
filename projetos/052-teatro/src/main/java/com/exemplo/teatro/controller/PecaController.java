package com.exemplo.teatro.controller;

import com.exemplo.teatro.model.Peca;
import com.exemplo.teatro.repository.PecaRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pecas")
public class PecaController {

    private final PecaRepository repository;

    public PecaController(PecaRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Peca> listar() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Peca> buscar(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Peca criar(@RequestBody Peca peca) {
        return repository.save(peca);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Peca> atualizar(@PathVariable Long id, @RequestBody Peca peca) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        peca.setId(id);
        return ResponseEntity.ok(repository.save(peca));
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
