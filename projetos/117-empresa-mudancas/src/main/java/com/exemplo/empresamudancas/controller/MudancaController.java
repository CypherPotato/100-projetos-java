package com.exemplo.empresamudancas.controller;

import com.exemplo.empresamudancas.model.Mudanca;
import com.exemplo.empresamudancas.repository.MudancaRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mudancas")
public class MudancaController {

    private final MudancaRepository repository;

    public MudancaController(MudancaRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Mudanca> listar(@RequestParam(required = false) Long clienteId, @RequestParam(required = false) Long caminhaoId) {
        if (clienteId != null) {
            return repository.findByClienteId(clienteId);
        }
        if (caminhaoId != null) {
            return repository.findByCaminhaoId(caminhaoId);
        }
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Mudanca> buscar(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mudanca criar(@RequestBody Mudanca mudanca) {
        return repository.save(mudanca);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Mudanca> atualizar(@PathVariable Long id, @RequestBody Mudanca mudanca) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        mudanca.setId(id);
        return ResponseEntity.ok(repository.save(mudanca));
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
