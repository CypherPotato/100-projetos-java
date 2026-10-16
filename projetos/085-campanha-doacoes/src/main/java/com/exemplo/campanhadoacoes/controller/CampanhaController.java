package com.exemplo.campanhadoacoes.controller;

import com.exemplo.campanhadoacoes.model.Campanha;
import com.exemplo.campanhadoacoes.repository.CampanhaRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/campanhas")
public class CampanhaController {

    private final CampanhaRepository repository;

    public CampanhaController(CampanhaRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Campanha> listar() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Campanha> buscar(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Campanha criar(@RequestBody Campanha campanha) {
        return repository.save(campanha);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Campanha> atualizar(@PathVariable Long id, @RequestBody Campanha campanha) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        campanha.setId(id);
        return ResponseEntity.ok(repository.save(campanha));
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
