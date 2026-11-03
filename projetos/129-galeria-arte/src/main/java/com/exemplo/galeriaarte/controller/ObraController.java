package com.exemplo.galeriaarte.controller;

import com.exemplo.galeriaarte.model.Obra;
import com.exemplo.galeriaarte.repository.ObraRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/obras")
public class ObraController {

    private final ObraRepository repository;

    public ObraController(ObraRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Obra> listar(@RequestParam(required = false) Long artistaId) {
        if (artistaId != null) {
            return repository.findByArtistaId(artistaId);
        }
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Obra> buscar(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Obra criar(@RequestBody Obra obra) {
        return repository.save(obra);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Obra> atualizar(@PathVariable Long id, @RequestBody Obra obra) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        obra.setId(id);
        return ResponseEntity.ok(repository.save(obra));
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
