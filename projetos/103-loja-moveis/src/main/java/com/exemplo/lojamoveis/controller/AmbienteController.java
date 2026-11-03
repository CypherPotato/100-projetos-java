package com.exemplo.lojamoveis.controller;

import com.exemplo.lojamoveis.model.Ambiente;
import com.exemplo.lojamoveis.repository.AmbienteRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ambientes")
public class AmbienteController {

    private final AmbienteRepository repository;

    public AmbienteController(AmbienteRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Ambiente> listar() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ambiente> buscar(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Ambiente criar(@RequestBody Ambiente ambiente) {
        return repository.save(ambiente);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Ambiente> atualizar(@PathVariable Long id, @RequestBody Ambiente ambiente) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        ambiente.setId(id);
        return ResponseEntity.ok(repository.save(ambiente));
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
