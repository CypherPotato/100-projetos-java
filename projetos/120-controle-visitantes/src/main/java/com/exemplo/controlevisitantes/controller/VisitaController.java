package com.exemplo.controlevisitantes.controller;

import com.exemplo.controlevisitantes.model.Visita;
import com.exemplo.controlevisitantes.repository.VisitaRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/visitas")
public class VisitaController {

    private final VisitaRepository repository;

    public VisitaController(VisitaRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Visita> listar(@RequestParam(required = false) Long anfitriaoId) {
        if (anfitriaoId != null) {
            return repository.findByAnfitriaoId(anfitriaoId);
        }
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Visita> buscar(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Visita criar(@RequestBody Visita visita) {
        return repository.save(visita);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Visita> atualizar(@PathVariable Long id, @RequestBody Visita visita) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        visita.setId(id);
        return ResponseEntity.ok(repository.save(visita));
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
