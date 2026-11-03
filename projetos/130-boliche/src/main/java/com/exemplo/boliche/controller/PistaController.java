package com.exemplo.boliche.controller;

import com.exemplo.boliche.model.Pista;
import com.exemplo.boliche.repository.PistaRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pistas")
public class PistaController {

    private final PistaRepository repository;

    public PistaController(PistaRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Pista> listar() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pista> buscar(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Pista criar(@RequestBody Pista pista) {
        return repository.save(pista);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pista> atualizar(@PathVariable Long id, @RequestBody Pista pista) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        pista.setId(id);
        return ResponseEntity.ok(repository.save(pista));
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
