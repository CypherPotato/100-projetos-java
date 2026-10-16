package com.exemplo.padaria.controller;

import com.exemplo.padaria.model.Fornada;
import com.exemplo.padaria.service.FornadaService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/fornadas")
public class FornadaController {

    private final FornadaService service;

    public FornadaController(FornadaService service) {
        this.service = service;
    }

    @GetMapping
    public List<Fornada> listar(@RequestParam(required = false) Long padeiroId) {
        return service.listar(padeiroId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Fornada> buscar(@PathVariable Long id) {
        return service.buscar(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Fornada criar(@RequestBody Fornada fornada) {
        return service.criar(fornada);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Fornada> atualizar(@PathVariable Long id, @RequestBody Fornada fornada) {
        return service.atualizar(id, fornada)
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
