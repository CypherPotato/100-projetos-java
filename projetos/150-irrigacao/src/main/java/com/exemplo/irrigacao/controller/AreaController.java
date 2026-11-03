package com.exemplo.irrigacao.controller;

import com.exemplo.irrigacao.model.Area;
import com.exemplo.irrigacao.repository.AreaRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/areas")
public class AreaController {

    private final AreaRepository repository;

    public AreaController(AreaRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Area> listar() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Area> buscar(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Area criar(@RequestBody Area area) {
        return repository.save(area);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Area> atualizar(@PathVariable Long id, @RequestBody Area area) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        area.setId(id);
        return ResponseEntity.ok(repository.save(area));
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
