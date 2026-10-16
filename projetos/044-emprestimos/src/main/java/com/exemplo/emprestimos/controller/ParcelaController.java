package com.exemplo.emprestimos.controller;

import com.exemplo.emprestimos.model.Parcela;
import com.exemplo.emprestimos.repository.ParcelaRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/parcelas")
public class ParcelaController {

    private final ParcelaRepository repository;

    public ParcelaController(ParcelaRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Parcela> listar(@RequestParam(required = false) Long emprestimoId) {
        if (emprestimoId != null) {
            return repository.findByEmprestimoId(emprestimoId);
        }
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Parcela> buscar(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Parcela criar(@RequestBody Parcela parcela) {
        return repository.save(parcela);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Parcela> atualizar(@PathVariable Long id, @RequestBody Parcela parcela) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        parcela.setId(id);
        return ResponseEntity.ok(repository.save(parcela));
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
