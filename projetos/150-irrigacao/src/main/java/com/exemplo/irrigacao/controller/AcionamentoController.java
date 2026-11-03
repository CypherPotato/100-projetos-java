package com.exemplo.irrigacao.controller;

import com.exemplo.irrigacao.model.Acionamento;
import com.exemplo.irrigacao.repository.AcionamentoRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/acionamentos")
public class AcionamentoController {

    private final AcionamentoRepository repository;

    public AcionamentoController(AcionamentoRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Acionamento> listar(@RequestParam(required = false) Long areaId) {
        if (areaId != null) {
            return repository.findByAreaId(areaId);
        }
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Acionamento> buscar(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Acionamento criar(@RequestBody Acionamento acionamento) {
        return repository.save(acionamento);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Acionamento> atualizar(@PathVariable Long id, @RequestBody Acionamento acionamento) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        acionamento.setId(id);
        return ResponseEntity.ok(repository.save(acionamento));
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
