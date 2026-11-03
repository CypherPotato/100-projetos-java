package com.exemplo.cafeicultura.controller;

import com.exemplo.cafeicultura.model.Colheita;
import com.exemplo.cafeicultura.repository.ColheitaRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/colheitas")
public class ColheitaController {

    private final ColheitaRepository repository;

    public ColheitaController(ColheitaRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Colheita> listar(@RequestParam(required = false) Long talhaoId) {
        if (talhaoId != null) {
            return repository.findByTalhaoId(talhaoId);
        }
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Colheita> buscar(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Colheita criar(@RequestBody Colheita colheita) {
        return repository.save(colheita);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Colheita> atualizar(@PathVariable Long id, @RequestBody Colheita colheita) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        colheita.setId(id);
        return ResponseEntity.ok(repository.save(colheita));
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
