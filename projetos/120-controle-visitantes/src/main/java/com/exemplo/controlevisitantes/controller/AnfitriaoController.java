package com.exemplo.controlevisitantes.controller;

import com.exemplo.controlevisitantes.model.Anfitriao;
import com.exemplo.controlevisitantes.repository.AnfitriaoRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/anfitrioes")
public class AnfitriaoController {

    private final AnfitriaoRepository repository;

    public AnfitriaoController(AnfitriaoRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Anfitriao> listar() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Anfitriao> buscar(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Anfitriao criar(@RequestBody Anfitriao anfitriao) {
        return repository.save(anfitriao);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Anfitriao> atualizar(@PathVariable Long id, @RequestBody Anfitriao anfitriao) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        anfitriao.setId(id);
        return ResponseEntity.ok(repository.save(anfitriao));
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
