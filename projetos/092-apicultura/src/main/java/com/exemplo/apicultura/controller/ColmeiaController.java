package com.exemplo.apicultura.controller;

import com.exemplo.apicultura.model.Colmeia;
import com.exemplo.apicultura.repository.ColmeiaRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/colmeias")
public class ColmeiaController {

    private final ColmeiaRepository repository;

    public ColmeiaController(ColmeiaRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Colmeia> listar(@RequestParam(required = false) Long apiarioId) {
        if (apiarioId != null) {
            return repository.findByApiarioId(apiarioId);
        }
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Colmeia> buscar(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Colmeia criar(@RequestBody Colmeia colmeia) {
        return repository.save(colmeia);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Colmeia> atualizar(@PathVariable Long id, @RequestBody Colmeia colmeia) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        colmeia.setId(id);
        return ResponseEntity.ok(repository.save(colmeia));
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
