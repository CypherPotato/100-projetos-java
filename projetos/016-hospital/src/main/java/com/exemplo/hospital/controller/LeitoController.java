package com.exemplo.hospital.controller;

import com.exemplo.hospital.model.Leito;
import com.exemplo.hospital.repository.LeitoRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/leitos")
public class LeitoController {

    private final LeitoRepository repository;

    public LeitoController(LeitoRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Leito> listar(@RequestParam(required = false) Long alaId) {
        if (alaId != null) {
            return repository.findByAlaId(alaId);
        }
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Leito> buscar(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Leito criar(@RequestBody Leito leito) {
        return repository.save(leito);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Leito> atualizar(@PathVariable Long id, @RequestBody Leito leito) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        leito.setId(id);
        return ResponseEntity.ok(repository.save(leito));
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
