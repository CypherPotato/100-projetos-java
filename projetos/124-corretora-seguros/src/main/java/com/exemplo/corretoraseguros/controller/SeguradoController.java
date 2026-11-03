package com.exemplo.corretoraseguros.controller;

import com.exemplo.corretoraseguros.model.Segurado;
import com.exemplo.corretoraseguros.repository.SeguradoRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/segurados")
public class SeguradoController {

    private final SeguradoRepository repository;

    public SeguradoController(SeguradoRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Segurado> listar() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Segurado> buscar(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Segurado criar(@RequestBody Segurado segurado) {
        return repository.save(segurado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Segurado> atualizar(@PathVariable Long id, @RequestBody Segurado segurado) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        segurado.setId(id);
        return ResponseEntity.ok(repository.save(segurado));
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
