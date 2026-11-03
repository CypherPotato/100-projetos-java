package com.exemplo.galeriaarte.controller;

import com.exemplo.galeriaarte.model.Artista;
import com.exemplo.galeriaarte.repository.ArtistaRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/artistas")
public class ArtistaController {

    private final ArtistaRepository repository;

    public ArtistaController(ArtistaRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Artista> listar() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Artista> buscar(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Artista criar(@RequestBody Artista artista) {
        return repository.save(artista);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Artista> atualizar(@PathVariable Long id, @RequestBody Artista artista) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        artista.setId(id);
        return ResponseEntity.ok(repository.save(artista));
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
