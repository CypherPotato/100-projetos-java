package com.exemplo.sorveteria.controller;

import com.exemplo.sorveteria.model.Sorvete;
import com.exemplo.sorveteria.repository.SorveteRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sorvetes")
public class SorveteController {

    private final SorveteRepository repository;

    public SorveteController(SorveteRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Sorvete> listar(@RequestParam(required = false) Long saborId) {
        if (saborId != null) {
            return repository.findBySaborId(saborId);
        }
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Sorvete> buscar(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Sorvete criar(@RequestBody Sorvete sorvete) {
        return repository.save(sorvete);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Sorvete> atualizar(@PathVariable Long id, @RequestBody Sorvete sorvete) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        sorvete.setId(id);
        return ResponseEntity.ok(repository.save(sorvete));
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
