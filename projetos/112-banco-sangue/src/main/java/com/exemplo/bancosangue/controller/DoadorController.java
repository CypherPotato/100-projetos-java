package com.exemplo.bancosangue.controller;

import com.exemplo.bancosangue.model.Doador;
import com.exemplo.bancosangue.repository.DoadorRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/doadores")
public class DoadorController {

    private final DoadorRepository repository;

    public DoadorController(DoadorRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Doador> listar() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Doador> buscar(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Doador criar(@RequestBody Doador doador) {
        return repository.save(doador);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Doador> atualizar(@PathVariable Long id, @RequestBody Doador doador) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        doador.setId(id);
        return ResponseEntity.ok(repository.save(doador));
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
