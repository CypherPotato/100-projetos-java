package com.exemplo.catalogoapis.controller;

import com.exemplo.catalogoapis.model.Equipe;
import com.exemplo.catalogoapis.service.EquipeService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/equipes")
public class EquipeController {

    private final EquipeService service;

    public EquipeController(EquipeService service) {
        this.service = service;
    }

    @GetMapping
    public List<Equipe> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Equipe> buscar(@PathVariable Long id) {
        return service.buscar(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Equipe criar(@RequestBody Equipe equipe) {
        return service.criar(equipe);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Equipe> atualizar(@PathVariable Long id, @RequestBody Equipe equipe) {
        return service.atualizar(id, equipe)
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
