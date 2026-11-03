package com.exemplo.gestaoobras.controller;

import com.exemplo.gestaoobras.model.Obra;
import com.exemplo.gestaoobras.service.ObraService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/obras")
public class ObraController {

    private final ObraService service;

    public ObraController(ObraService service) {
        this.service = service;
    }

    @GetMapping
    public List<Obra> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Obra> buscar(@PathVariable Long id) {
        return service.buscar(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Obra criar(@RequestBody Obra obra) {
        return service.criar(obra);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Obra> atualizar(@PathVariable Long id, @RequestBody Obra obra) {
        return service.atualizar(id, obra)
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
