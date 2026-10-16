package com.exemplo.lojagames.controller;

import com.exemplo.lojagames.model.Plataforma;
import com.exemplo.lojagames.repository.PlataformaRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/plataformas")
public class PlataformaController {

    private final PlataformaRepository repository;

    public PlataformaController(PlataformaRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Plataforma> listar() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Plataforma> buscar(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Plataforma criar(@RequestBody Plataforma plataforma) {
        return repository.save(plataforma);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Plataforma> atualizar(@PathVariable Long id, @RequestBody Plataforma plataforma) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        plataforma.setId(id);
        return ResponseEntity.ok(repository.save(plataforma));
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
