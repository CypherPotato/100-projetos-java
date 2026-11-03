package com.exemplo.escoteiros.controller;

import com.exemplo.escoteiros.model.Patrulha;
import com.exemplo.escoteiros.service.PatrulhaService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/patrulhas")
public class PatrulhaController {

    private final PatrulhaService service;

    public PatrulhaController(PatrulhaService service) {
        this.service = service;
    }

    @GetMapping
    public List<Patrulha> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Patrulha> buscar(@PathVariable Long id) {
        return service.buscar(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Patrulha criar(@RequestBody Patrulha patrulha) {
        return service.criar(patrulha);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Patrulha> atualizar(@PathVariable Long id, @RequestBody Patrulha patrulha) {
        return service.atualizar(id, patrulha)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        if (!service.excluir(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
