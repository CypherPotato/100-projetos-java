package com.exemplo.associacaobairro.controller;

import com.exemplo.associacaobairro.model.Associado;
import com.exemplo.associacaobairro.repository.AssociadoRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/associados")
public class AssociadoController {

    private final AssociadoRepository repository;

    public AssociadoController(AssociadoRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Associado> listar(@RequestParam(required = false) Long ruaId) {
        if (ruaId != null) {
            return repository.findByRuaId(ruaId);
        }
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Associado> buscar(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Associado criar(@RequestBody Associado associado) {
        return repository.save(associado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Associado> atualizar(@PathVariable Long id, @RequestBody Associado associado) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        associado.setId(id);
        return ResponseEntity.ok(repository.save(associado));
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
