package com.exemplo.servidores.controller;

import com.exemplo.servidores.model.Aplicacao;
import com.exemplo.servidores.repository.AplicacaoRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/aplicacoes")
public class AplicacaoController {

    private final AplicacaoRepository repository;

    public AplicacaoController(AplicacaoRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Aplicacao> listar(@RequestParam(required = false) Long servidorId) {
        if (servidorId != null) {
            return repository.findByServidorId(servidorId);
        }
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Aplicacao> buscar(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Aplicacao criar(@RequestBody Aplicacao aplicacao) {
        return repository.save(aplicacao);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Aplicacao> atualizar(@PathVariable Long id, @RequestBody Aplicacao aplicacao) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        aplicacao.setId(id);
        return ResponseEntity.ok(repository.save(aplicacao));
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
