package com.exemplo.contasbancarias.controller;

import com.exemplo.contasbancarias.model.ContaBancaria;
import com.exemplo.contasbancarias.repository.ContaBancariaRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/contas-bancarias")
public class ContaBancariaController {

    private final ContaBancariaRepository repository;

    public ContaBancariaController(ContaBancariaRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<ContaBancaria> listar(@RequestParam(required = false) Long bancoId) {
        if (bancoId != null) {
            return repository.findByBancoId(bancoId);
        }
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContaBancaria> buscar(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ContaBancaria criar(@RequestBody ContaBancaria contaBancaria) {
        return repository.save(contaBancaria);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ContaBancaria> atualizar(@PathVariable Long id, @RequestBody ContaBancaria contaBancaria) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        contaBancaria.setId(id);
        return ResponseEntity.ok(repository.save(contaBancaria));
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
