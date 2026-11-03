package com.exemplo.estudiofotografia.controller;

import com.exemplo.estudiofotografia.model.Ensaio;
import com.exemplo.estudiofotografia.service.EnsaioService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ensaios")
public class EnsaioController {

    private final EnsaioService service;

    public EnsaioController(EnsaioService service) {
        this.service = service;
    }

    @GetMapping
    public List<Ensaio> listar(@RequestParam(required = false) Long clienteId) {
        return service.listar(clienteId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ensaio> buscar(@PathVariable Long id) {
        return service.buscar(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Ensaio criar(@RequestBody Ensaio ensaio) {
        return service.criar(ensaio);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Ensaio> atualizar(@PathVariable Long id, @RequestBody Ensaio ensaio) {
        return service.atualizar(id, ensaio)
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
