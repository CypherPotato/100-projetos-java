package com.exemplo.cinema.controller;

import com.exemplo.cinema.model.Sessao;
import com.exemplo.cinema.repository.SessaoRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sessoes")
public class SessaoController {

    private final SessaoRepository repository;

    public SessaoController(SessaoRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Sessao> listar(@RequestParam(required = false) Long filmeId) {
        if (filmeId != null) {
            return repository.findByFilmeId(filmeId);
        }
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Sessao> buscar(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Sessao criar(@RequestBody Sessao sessao) {
        return repository.save(sessao);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Sessao> atualizar(@PathVariable Long id, @RequestBody Sessao sessao) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        sessao.setId(id);
        return ResponseEntity.ok(repository.save(sessao));
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
