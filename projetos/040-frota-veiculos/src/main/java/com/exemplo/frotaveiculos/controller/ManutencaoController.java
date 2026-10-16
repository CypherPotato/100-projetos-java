package com.exemplo.frotaveiculos.controller;

import com.exemplo.frotaveiculos.model.Manutencao;
import com.exemplo.frotaveiculos.repository.ManutencaoRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/manutencoes")
public class ManutencaoController {

    private final ManutencaoRepository repository;

    public ManutencaoController(ManutencaoRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Manutencao> listar(@RequestParam(required = false) Long veiculoId) {
        if (veiculoId != null) {
            return repository.findByVeiculoId(veiculoId);
        }
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Manutencao> buscar(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Manutencao criar(@RequestBody Manutencao manutencao) {
        return repository.save(manutencao);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Manutencao> atualizar(@PathVariable Long id, @RequestBody Manutencao manutencao) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        manutencao.setId(id);
        return ResponseEntity.ok(repository.save(manutencao));
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
