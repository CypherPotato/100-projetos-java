package com.exemplo.bancoalimentos.controller;

import com.exemplo.bancoalimentos.model.Alimento;
import com.exemplo.bancoalimentos.repository.AlimentoRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/alimentos")
public class AlimentoController {

    private final AlimentoRepository repository;

    public AlimentoController(AlimentoRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Alimento> listar(@RequestParam(required = false) Long doadorId) {
        if (doadorId != null) {
            return repository.findByDoadorId(doadorId);
        }
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Alimento> buscar(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Alimento criar(@RequestBody Alimento alimento) {
        return repository.save(alimento);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Alimento> atualizar(@PathVariable Long id, @RequestBody Alimento alimento) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        alimento.setId(id);
        return ResponseEntity.ok(repository.save(alimento));
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
