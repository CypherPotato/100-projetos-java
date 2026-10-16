package com.exemplo.locadora.controller;

import com.exemplo.locadora.model.Locacao;
import com.exemplo.locadora.repository.LocacaoRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/locacoes")
public class LocacaoController {

    private final LocacaoRepository repository;

    public LocacaoController(LocacaoRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Locacao> listar(@RequestParam(required = false) Long veiculoId) {
        if (veiculoId != null) {
            return repository.findByVeiculoId(veiculoId);
        }
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Locacao> buscar(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Locacao criar(@RequestBody Locacao locacao) {
        return repository.save(locacao);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Locacao> atualizar(@PathVariable Long id, @RequestBody Locacao locacao) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        locacao.setId(id);
        return ResponseEntity.ok(repository.save(locacao));
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
