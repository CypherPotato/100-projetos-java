package com.exemplo.viveiro.controller;

import com.exemplo.viveiro.model.Especie;
import com.exemplo.viveiro.service.EspecieService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/especies")
public class EspecieController {

    private final EspecieService service;

    public EspecieController(EspecieService service) {
        this.service = service;
    }

    @GetMapping
    public List<Especie> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Especie> buscar(@PathVariable Long id) {
        return service.buscar(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Especie criar(@RequestBody Especie especie) {
        return service.criar(especie);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Especie> atualizar(@PathVariable Long id, @RequestBody Especie especie) {
        return service.atualizar(id, especie)
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
