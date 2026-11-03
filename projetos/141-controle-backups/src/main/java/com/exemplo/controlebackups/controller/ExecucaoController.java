package com.exemplo.controlebackups.controller;

import com.exemplo.controlebackups.model.Execucao;
import com.exemplo.controlebackups.repository.ExecucaoRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/execucoes")
public class ExecucaoController {

    private final ExecucaoRepository repository;

    public ExecucaoController(ExecucaoRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Execucao> listar(@RequestParam(required = false) Long rotinaId) {
        if (rotinaId != null) {
            return repository.findByRotinaId(rotinaId);
        }
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Execucao> buscar(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Execucao criar(@RequestBody Execucao execucao) {
        return repository.save(execucao);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Execucao> atualizar(@PathVariable Long id, @RequestBody Execucao execucao) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        execucao.setId(id);
        return ResponseEntity.ok(repository.save(execucao));
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
