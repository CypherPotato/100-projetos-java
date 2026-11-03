package com.exemplo.hortacomunitaria.controller;

import com.exemplo.hortacomunitaria.model.Morador;
import com.exemplo.hortacomunitaria.repository.MoradorRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/moradores")
public class MoradorController {

    private final MoradorRepository repository;

    public MoradorController(MoradorRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Morador> listar() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Morador> buscar(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Morador criar(@RequestBody Morador morador) {
        return repository.save(morador);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Morador> atualizar(@PathVariable Long id, @RequestBody Morador morador) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        morador.setId(id);
        return ResponseEntity.ok(repository.save(morador));
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
