package com.exemplo.corretoraseguros.controller;

import com.exemplo.corretoraseguros.model.Sinistro;
import com.exemplo.corretoraseguros.repository.SinistroRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sinistros")
public class SinistroController {

    private final SinistroRepository repository;

    public SinistroController(SinistroRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Sinistro> listar(@RequestParam(required = false) Long apoliceId) {
        if (apoliceId != null) {
            return repository.findByApoliceId(apoliceId);
        }
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Sinistro> buscar(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Sinistro criar(@RequestBody Sinistro sinistro) {
        return repository.save(sinistro);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Sinistro> atualizar(@PathVariable Long id, @RequestBody Sinistro sinistro) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        sinistro.setId(id);
        return ResponseEntity.ok(repository.save(sinistro));
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
