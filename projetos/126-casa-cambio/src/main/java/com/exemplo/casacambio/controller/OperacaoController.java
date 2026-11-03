package com.exemplo.casacambio.controller;

import com.exemplo.casacambio.model.Operacao;
import com.exemplo.casacambio.repository.OperacaoRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/operacoes")
public class OperacaoController {

    private final OperacaoRepository repository;

    public OperacaoController(OperacaoRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Operacao> listar(@RequestParam(required = false) Long moedaId) {
        if (moedaId != null) {
            return repository.findByMoedaId(moedaId);
        }
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Operacao> buscar(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Operacao criar(@RequestBody Operacao operacao) {
        return repository.save(operacao);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Operacao> atualizar(@PathVariable Long id, @RequestBody Operacao operacao) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        operacao.setId(id);
        return ResponseEntity.ok(repository.save(operacao));
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
