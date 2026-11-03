package com.exemplo.acougue.controller;

import com.exemplo.acougue.model.Corte;
import com.exemplo.acougue.service.CorteService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cortes")
public class CorteController {

    private final CorteService service;

    public CorteController(CorteService service) {
        this.service = service;
    }

    @GetMapping
    public List<Corte> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Corte> buscar(@PathVariable Long id) {
        return service.buscar(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Corte criar(@RequestBody Corte corte) {
        return service.criar(corte);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Corte> atualizar(@PathVariable Long id, @RequestBody Corte corte) {
        return service.atualizar(id, corte)
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
