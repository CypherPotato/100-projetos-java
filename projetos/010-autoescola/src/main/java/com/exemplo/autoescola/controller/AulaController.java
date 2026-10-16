package com.exemplo.autoescola.controller;

import com.exemplo.autoescola.model.Aula;
import com.exemplo.autoescola.repository.AulaRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/aulas")
public class AulaController {

    private final AulaRepository repository;

    public AulaController(AulaRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Aula> listar(@RequestParam(required = false) Long instrutorId, @RequestParam(required = false) Long veiculoId) {
        if (instrutorId != null) {
            return repository.findByInstrutorId(instrutorId);
        }
        if (veiculoId != null) {
            return repository.findByVeiculoId(veiculoId);
        }
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Aula> buscar(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Aula criar(@RequestBody Aula aula) {
        return repository.save(aula);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Aula> atualizar(@PathVariable Long id, @RequestBody Aula aula) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        aula.setId(id);
        return ResponseEntity.ok(repository.save(aula));
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
