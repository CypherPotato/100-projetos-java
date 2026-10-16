package com.exemplo.adega.controller;

import com.exemplo.adega.model.Vinho;
import com.exemplo.adega.service.VinhoService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/vinhos")
public class VinhoController {

    private final VinhoService service;

    public VinhoController(VinhoService service) {
        this.service = service;
    }

    @GetMapping
    public List<Vinho> listar(@RequestParam(required = false) Long vinicolaId) {
        return service.listar(vinicolaId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Vinho> buscar(@PathVariable Long id) {
        return service.buscar(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Vinho criar(@RequestBody Vinho vinho) {
        return service.criar(vinho);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Vinho> atualizar(@PathVariable Long id, @RequestBody Vinho vinho) {
        return service.atualizar(id, vinho)
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
