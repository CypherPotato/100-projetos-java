package com.exemplo.controlebackups.controller;

import com.exemplo.controlebackups.model.Rotina;
import com.exemplo.controlebackups.repository.RotinaRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rotinas")
public class RotinaController {

    private final RotinaRepository repository;

    public RotinaController(RotinaRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Rotina> listar() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Rotina> buscar(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Rotina criar(@RequestBody Rotina rotina) {
        return repository.save(rotina);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Rotina> atualizar(@PathVariable Long id, @RequestBody Rotina rotina) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        rotina.setId(id);
        return ResponseEntity.ok(repository.save(rotina));
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
