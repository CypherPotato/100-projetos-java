package com.exemplo.hortifruti.controller;

import com.exemplo.hortifruti.model.Produtor;
import com.exemplo.hortifruti.repository.ProdutorRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/produtores")
public class ProdutorController {

    private final ProdutorRepository repository;

    public ProdutorController(ProdutorRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Produtor> listar() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Produtor> buscar(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Produtor criar(@RequestBody Produtor produtor) {
        return repository.save(produtor);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Produtor> atualizar(@PathVariable Long id, @RequestBody Produtor produtor) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        produtor.setId(id);
        return ResponseEntity.ok(repository.save(produtor));
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
