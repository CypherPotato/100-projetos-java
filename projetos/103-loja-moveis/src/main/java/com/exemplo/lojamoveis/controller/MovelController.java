package com.exemplo.lojamoveis.controller;

import com.exemplo.lojamoveis.model.Movel;
import com.exemplo.lojamoveis.repository.MovelRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/moveis")
public class MovelController {

    private final MovelRepository repository;

    public MovelController(MovelRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Movel> listar(@RequestParam(required = false) Long ambienteId) {
        if (ambienteId != null) {
            return repository.findByAmbienteId(ambienteId);
        }
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Movel> buscar(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Movel criar(@RequestBody Movel movel) {
        return repository.save(movel);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Movel> atualizar(@PathVariable Long id, @RequestBody Movel movel) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        movel.setId(id);
        return ResponseEntity.ok(repository.save(movel));
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
