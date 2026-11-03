package com.exemplo.perfumaria.controller;

import com.exemplo.perfumaria.model.Perfume;
import com.exemplo.perfumaria.service.PerfumeService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/perfumes")
public class PerfumeController {

    private final PerfumeService service;

    public PerfumeController(PerfumeService service) {
        this.service = service;
    }

    @GetMapping
    public List<Perfume> listar(@RequestParam(required = false) Long fabricanteId) {
        return service.listar(fabricanteId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Perfume> buscar(@PathVariable Long id) {
        return service.buscar(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Perfume criar(@RequestBody Perfume perfume) {
        return service.criar(perfume);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Perfume> atualizar(@PathVariable Long id, @RequestBody Perfume perfume) {
        return service.atualizar(id, perfume)
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
