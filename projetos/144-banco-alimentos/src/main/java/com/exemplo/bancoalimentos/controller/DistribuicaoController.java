package com.exemplo.bancoalimentos.controller;

import com.exemplo.bancoalimentos.model.Distribuicao;
import com.exemplo.bancoalimentos.repository.DistribuicaoRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/distribuicoes")
public class DistribuicaoController {

    private final DistribuicaoRepository repository;

    public DistribuicaoController(DistribuicaoRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Distribuicao> listar(@RequestParam(required = false) Long alimentoId) {
        if (alimentoId != null) {
            return repository.findByAlimentoId(alimentoId);
        }
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Distribuicao> buscar(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Distribuicao criar(@RequestBody Distribuicao distribuicao) {
        return repository.save(distribuicao);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Distribuicao> atualizar(@PathVariable Long id, @RequestBody Distribuicao distribuicao) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        distribuicao.setId(id);
        return ResponseEntity.ok(repository.save(distribuicao));
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
