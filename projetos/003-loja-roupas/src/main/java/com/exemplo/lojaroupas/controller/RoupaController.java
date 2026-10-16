package com.exemplo.lojaroupas.controller;

import com.exemplo.lojaroupas.model.Roupa;
import com.exemplo.lojaroupas.service.RoupaService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/roupas")
public class RoupaController {

    private final RoupaService service;

    public RoupaController(RoupaService service) {
        this.service = service;
    }

    @GetMapping
    public List<Roupa> listar(@RequestParam(required = false) Long categoriaId) {
        return service.listar(categoriaId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Roupa> buscar(@PathVariable Long id) {
        return service.buscar(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Roupa criar(@RequestBody Roupa roupa) {
        return service.criar(roupa);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Roupa> atualizar(@PathVariable Long id, @RequestBody Roupa roupa) {
        return service.atualizar(id, roupa)
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
