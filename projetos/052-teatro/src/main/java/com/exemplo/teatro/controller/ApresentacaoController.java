package com.exemplo.teatro.controller;

import com.exemplo.teatro.model.Apresentacao;
import com.exemplo.teatro.repository.ApresentacaoRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/apresentacoes")
public class ApresentacaoController {

    private final ApresentacaoRepository repository;

    public ApresentacaoController(ApresentacaoRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Apresentacao> listar(@RequestParam(required = false) Long pecaId) {
        if (pecaId != null) {
            return repository.findByPecaId(pecaId);
        }
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Apresentacao> buscar(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Apresentacao criar(@RequestBody Apresentacao apresentacao) {
        return repository.save(apresentacao);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Apresentacao> atualizar(@PathVariable Long id, @RequestBody Apresentacao apresentacao) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        apresentacao.setId(id);
        return ResponseEntity.ok(repository.save(apresentacao));
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
