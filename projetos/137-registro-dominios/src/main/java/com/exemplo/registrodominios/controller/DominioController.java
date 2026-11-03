package com.exemplo.registrodominios.controller;

import com.exemplo.registrodominios.model.Dominio;
import com.exemplo.registrodominios.service.DominioService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/dominios")
public class DominioController {

    private final DominioService service;

    public DominioController(DominioService service) {
        this.service = service;
    }

    @GetMapping
    public List<Dominio> listar(@RequestParam(required = false) Long registradorId) {
        return service.listar(registradorId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Dominio> buscar(@PathVariable Long id) {
        return service.buscar(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Dominio criar(@RequestBody Dominio dominio) {
        return service.criar(dominio);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Dominio> atualizar(@PathVariable Long id, @RequestBody Dominio dominio) {
        return service.atualizar(id, dominio)
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
