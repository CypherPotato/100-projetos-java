package com.exemplo.contasapagar.controller;

import com.exemplo.contasapagar.model.ContaPagar;
import com.exemplo.contasapagar.repository.ContaPagarRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/contas-pagar")
public class ContaPagarController {

    private final ContaPagarRepository repository;

    public ContaPagarController(ContaPagarRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<ContaPagar> listar(@RequestParam(required = false) Long fornecedorId) {
        if (fornecedorId != null) {
            return repository.findByFornecedorId(fornecedorId);
        }
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContaPagar> buscar(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ContaPagar criar(@RequestBody ContaPagar contaPagar) {
        return repository.save(contaPagar);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ContaPagar> atualizar(@PathVariable Long id, @RequestBody ContaPagar contaPagar) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        contaPagar.setId(id);
        return ResponseEntity.ok(repository.save(contaPagar));
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
