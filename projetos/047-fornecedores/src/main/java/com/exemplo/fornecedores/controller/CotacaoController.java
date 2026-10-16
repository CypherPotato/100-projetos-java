package com.exemplo.fornecedores.controller;

import com.exemplo.fornecedores.model.Cotacao;
import com.exemplo.fornecedores.repository.CotacaoRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cotacoes")
public class CotacaoController {

    private final CotacaoRepository repository;

    public CotacaoController(CotacaoRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Cotacao> listar(@RequestParam(required = false) Long fornecedorId) {
        if (fornecedorId != null) {
            return repository.findByFornecedorId(fornecedorId);
        }
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cotacao> buscar(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cotacao criar(@RequestBody Cotacao cotacao) {
        return repository.save(cotacao);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cotacao> atualizar(@PathVariable Long id, @RequestBody Cotacao cotacao) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        cotacao.setId(id);
        return ResponseEntity.ok(repository.save(cotacao));
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
