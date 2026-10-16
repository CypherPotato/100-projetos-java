package com.exemplo.dentista.controller;

import com.exemplo.dentista.model.Dentista;
import com.exemplo.dentista.repository.DentistaRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/dentistas")
public class DentistaController {

    private final DentistaRepository repository;

    public DentistaController(DentistaRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Dentista> listar() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Dentista> buscar(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Dentista criar(@RequestBody Dentista dentista) {
        return repository.save(dentista);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Dentista> atualizar(@PathVariable Long id, @RequestBody Dentista dentista) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        dentista.setId(id);
        return ResponseEntity.ok(repository.save(dentista));
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
