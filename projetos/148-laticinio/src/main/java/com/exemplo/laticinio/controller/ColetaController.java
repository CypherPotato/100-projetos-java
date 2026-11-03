package com.exemplo.laticinio.controller;

import com.exemplo.laticinio.model.Coleta;
import com.exemplo.laticinio.repository.ColetaRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/coletas")
public class ColetaController {

    private final ColetaRepository repository;

    public ColetaController(ColetaRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Coleta> listar(@RequestParam(required = false) Long produtorId) {
        if (produtorId != null) {
            return repository.findByProdutorId(produtorId);
        }
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Coleta> buscar(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Coleta criar(@RequestBody Coleta coleta) {
        return repository.save(coleta);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Coleta> atualizar(@PathVariable Long id, @RequestBody Coleta coleta) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        coleta.setId(id);
        return ResponseEntity.ok(repository.save(coleta));
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
