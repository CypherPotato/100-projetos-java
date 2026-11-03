package com.exemplo.lojaesportes.controller;

import com.exemplo.lojaesportes.model.Artigo;
import com.exemplo.lojaesportes.repository.ArtigoRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/artigos")
public class ArtigoController {

    private final ArtigoRepository repository;

    public ArtigoController(ArtigoRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Artigo> listar(@RequestParam(required = false) Long modalidadeId) {
        if (modalidadeId != null) {
            return repository.findByModalidadeId(modalidadeId);
        }
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Artigo> buscar(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Artigo criar(@RequestBody Artigo artigo) {
        return repository.save(artigo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Artigo> atualizar(@PathVariable Long id, @RequestBody Artigo artigo) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        artigo.setId(id);
        return ResponseEntity.ok(repository.save(artigo));
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
