package com.exemplo.hortacomunitaria.controller;

import com.exemplo.hortacomunitaria.model.Plantio;
import com.exemplo.hortacomunitaria.repository.PlantioRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/plantios")
public class PlantioController {

    private final PlantioRepository repository;

    public PlantioController(PlantioRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Plantio> listar(@RequestParam(required = false) Long canteiroId) {
        if (canteiroId != null) {
            return repository.findByCanteiroId(canteiroId);
        }
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Plantio> buscar(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Plantio criar(@RequestBody Plantio plantio) {
        return repository.save(plantio);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Plantio> atualizar(@PathVariable Long id, @RequestBody Plantio plantio) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        plantio.setId(id);
        return ResponseEntity.ok(repository.save(plantio));
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
