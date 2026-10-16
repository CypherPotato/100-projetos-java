package com.exemplo.controleestoque.controller;

import com.exemplo.controleestoque.model.Movimentacao;
import com.exemplo.controleestoque.repository.MovimentacaoRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/movimentacoes")
public class MovimentacaoController {

    private final MovimentacaoRepository repository;

    public MovimentacaoController(MovimentacaoRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Movimentacao> listar(@RequestParam(required = false) Long produtoId) {
        if (produtoId != null) {
            return repository.findByProdutoId(produtoId);
        }
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Movimentacao> buscar(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Movimentacao criar(@RequestBody Movimentacao movimentacao) {
        return repository.save(movimentacao);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Movimentacao> atualizar(@PathVariable Long id, @RequestBody Movimentacao movimentacao) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        movimentacao.setId(id);
        return ResponseEntity.ok(repository.save(movimentacao));
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
