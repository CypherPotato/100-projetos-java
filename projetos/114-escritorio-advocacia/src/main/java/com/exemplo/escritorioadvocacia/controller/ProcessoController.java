package com.exemplo.escritorioadvocacia.controller;

import com.exemplo.escritorioadvocacia.model.Processo;
import com.exemplo.escritorioadvocacia.repository.ProcessoRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/processos")
public class ProcessoController {

    private final ProcessoRepository repository;

    public ProcessoController(ProcessoRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Processo> listar(@RequestParam(required = false) Long clienteId) {
        if (clienteId != null) {
            return repository.findByClienteId(clienteId);
        }
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Processo> buscar(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Processo criar(@RequestBody Processo processo) {
        return repository.save(processo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Processo> atualizar(@PathVariable Long id, @RequestBody Processo processo) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        processo.setId(id);
        return ResponseEntity.ok(repository.save(processo));
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
