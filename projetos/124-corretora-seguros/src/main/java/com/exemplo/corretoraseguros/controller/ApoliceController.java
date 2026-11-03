package com.exemplo.corretoraseguros.controller;

import com.exemplo.corretoraseguros.model.Apolice;
import com.exemplo.corretoraseguros.repository.ApoliceRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/apolices")
public class ApoliceController {

    private final ApoliceRepository repository;

    public ApoliceController(ApoliceRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Apolice> listar(@RequestParam(required = false) Long seguradoId) {
        if (seguradoId != null) {
            return repository.findBySeguradoId(seguradoId);
        }
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Apolice> buscar(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Apolice criar(@RequestBody Apolice apolice) {
        return repository.save(apolice);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Apolice> atualizar(@PathVariable Long id, @RequestBody Apolice apolice) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        apolice.setId(id);
        return ResponseEntity.ok(repository.save(apolice));
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
