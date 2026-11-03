package com.exemplo.perfumaria.controller;

import com.exemplo.perfumaria.model.Fabricante;
import com.exemplo.perfumaria.service.FabricanteService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/fabricantes")
public class FabricanteController {

    private final FabricanteService service;

    public FabricanteController(FabricanteService service) {
        this.service = service;
    }

    @GetMapping
    public List<Fabricante> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Fabricante> buscar(@PathVariable Long id) {
        return service.buscar(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Fabricante criar(@RequestBody Fabricante fabricante) {
        return service.criar(fabricante);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Fabricante> atualizar(@PathVariable Long id, @RequestBody Fabricante fabricante) {
        return service.atualizar(id, fabricante)
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
