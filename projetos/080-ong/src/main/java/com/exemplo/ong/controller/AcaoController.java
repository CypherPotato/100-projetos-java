package com.exemplo.ong.controller;

import com.exemplo.ong.model.Acao;
import com.exemplo.ong.repository.AcaoRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/acoes")
public class AcaoController {

    private final AcaoRepository repository;

    public AcaoController(AcaoRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Acao> listar() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Acao> buscar(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Acao criar(@RequestBody Acao acao) {
        return repository.save(acao);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Acao> atualizar(@PathVariable Long id, @RequestBody Acao acao) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        acao.setId(id);
        return ResponseEntity.ok(repository.save(acao));
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
