package com.exemplo.laticinio.controller;

import com.exemplo.laticinio.model.Lote;
import com.exemplo.laticinio.repository.LoteRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/lotes")
public class LoteController {

    private final LoteRepository repository;

    public LoteController(LoteRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Lote> listar(@RequestParam(required = false) Long coletaId) {
        if (coletaId != null) {
            return repository.findByColetaId(coletaId);
        }
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Lote> buscar(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Lote criar(@RequestBody Lote lote) {
        return repository.save(lote);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Lote> atualizar(@PathVariable Long id, @RequestBody Lote lote) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        lote.setId(id);
        return ResponseEntity.ok(repository.save(lote));
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
