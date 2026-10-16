package com.exemplo.cooperativa.controller;

import com.exemplo.cooperativa.model.Cooperado;
import com.exemplo.cooperativa.service.CooperadoService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cooperados")
public class CooperadoController {

    private final CooperadoService service;

    public CooperadoController(CooperadoService service) {
        this.service = service;
    }

    @GetMapping
    public List<Cooperado> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cooperado> buscar(@PathVariable Long id) {
        return service.buscar(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cooperado criar(@RequestBody Cooperado cooperado) {
        return service.criar(cooperado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cooperado> atualizar(@PathVariable Long id, @RequestBody Cooperado cooperado) {
        return service.atualizar(id, cooperado)
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
