package com.exemplo.casashows.controller;

import com.exemplo.casashows.model.Ingresso;
import com.exemplo.casashows.service.IngressoService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ingressos")
public class IngressoController {

    private final IngressoService service;

    public IngressoController(IngressoService service) {
        this.service = service;
    }

    @GetMapping
    public List<Ingresso> listar(@RequestParam(required = false) Long showId) {
        return service.listar(showId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ingresso> buscar(@PathVariable Long id) {
        return service.buscar(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Ingresso criar(@RequestBody Ingresso ingresso) {
        return service.criar(ingresso);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Ingresso> atualizar(@PathVariable Long id, @RequestBody Ingresso ingresso) {
        return service.atualizar(id, ingresso)
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
