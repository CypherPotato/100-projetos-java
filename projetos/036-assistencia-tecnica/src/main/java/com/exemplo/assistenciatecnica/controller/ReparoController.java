package com.exemplo.assistenciatecnica.controller;

import com.exemplo.assistenciatecnica.model.Reparo;
import com.exemplo.assistenciatecnica.service.ReparoService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reparos")
public class ReparoController {

    private final ReparoService service;

    public ReparoController(ReparoService service) {
        this.service = service;
    }

    @GetMapping
    public List<Reparo> listar(@RequestParam(required = false) Long aparelhoId) {
        return service.listar(aparelhoId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reparo> buscar(@PathVariable Long id) {
        return service.buscar(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Reparo criar(@RequestBody Reparo reparo) {
        return service.criar(reparo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Reparo> atualizar(@PathVariable Long id, @RequestBody Reparo reparo) {
        return service.atualizar(id, reparo)
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
