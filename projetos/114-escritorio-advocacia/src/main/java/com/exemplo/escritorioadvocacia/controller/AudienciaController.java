package com.exemplo.escritorioadvocacia.controller;

import com.exemplo.escritorioadvocacia.model.Audiencia;
import com.exemplo.escritorioadvocacia.repository.AudienciaRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/audiencias")
public class AudienciaController {

    private final AudienciaRepository repository;

    public AudienciaController(AudienciaRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Audiencia> listar(@RequestParam(required = false) Long processoId) {
        if (processoId != null) {
            return repository.findByProcessoId(processoId);
        }
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Audiencia> buscar(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Audiencia criar(@RequestBody Audiencia audiencia) {
        return repository.save(audiencia);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Audiencia> atualizar(@PathVariable Long id, @RequestBody Audiencia audiencia) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        audiencia.setId(id);
        return ResponseEntity.ok(repository.save(audiencia));
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
