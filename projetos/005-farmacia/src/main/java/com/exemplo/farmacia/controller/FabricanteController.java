package com.exemplo.farmacia.controller;

import com.exemplo.farmacia.model.Fabricante;
import com.exemplo.farmacia.repository.FabricanteRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/fabricantes")
public class FabricanteController {

    private final FabricanteRepository repository;

    public FabricanteController(FabricanteRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Fabricante> listar() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Fabricante> buscar(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Fabricante criar(@RequestBody Fabricante fabricante) {
        return repository.save(fabricante);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Fabricante> atualizar(@PathVariable Long id, @RequestBody Fabricante fabricante) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        fabricante.setId(id);
        return ResponseEntity.ok(repository.save(fabricante));
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
