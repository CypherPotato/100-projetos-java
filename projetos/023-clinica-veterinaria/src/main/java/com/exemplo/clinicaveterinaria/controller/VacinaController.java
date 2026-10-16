package com.exemplo.clinicaveterinaria.controller;

import com.exemplo.clinicaveterinaria.model.Vacina;
import com.exemplo.clinicaveterinaria.repository.VacinaRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/vacinas")
public class VacinaController {

    private final VacinaRepository repository;

    public VacinaController(VacinaRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Vacina> listar(@RequestParam(required = false) Long animalId) {
        if (animalId != null) {
            return repository.findByAnimalId(animalId);
        }
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Vacina> buscar(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Vacina criar(@RequestBody Vacina vacina) {
        return repository.save(vacina);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Vacina> atualizar(@PathVariable Long id, @RequestBody Vacina vacina) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        vacina.setId(id);
        return ResponseEntity.ok(repository.save(vacina));
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
