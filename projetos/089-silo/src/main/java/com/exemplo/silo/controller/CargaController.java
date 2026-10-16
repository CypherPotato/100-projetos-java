package com.exemplo.silo.controller;

import com.exemplo.silo.model.Carga;
import com.exemplo.silo.repository.CargaRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cargas")
public class CargaController {

    private final CargaRepository repository;

    public CargaController(CargaRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Carga> listar(@RequestParam(required = false) Long siloId) {
        if (siloId != null) {
            return repository.findBySiloId(siloId);
        }
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Carga> buscar(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Carga criar(@RequestBody Carga carga) {
        return repository.save(carga);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Carga> atualizar(@PathVariable Long id, @RequestBody Carga carga) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        carga.setId(id);
        return ResponseEntity.ok(repository.save(carga));
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
