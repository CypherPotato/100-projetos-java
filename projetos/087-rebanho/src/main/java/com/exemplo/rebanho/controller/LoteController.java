package com.exemplo.rebanho.controller;

import com.exemplo.rebanho.model.Lote;
import com.exemplo.rebanho.service.LoteService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/lotes")
public class LoteController {

    private final LoteService service;

    public LoteController(LoteService service) {
        this.service = service;
    }

    @GetMapping
    public List<Lote> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Lote> buscar(@PathVariable Long id) {
        return service.buscar(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Lote criar(@RequestBody Lote lote) {
        return service.criar(lote);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Lote> atualizar(@PathVariable Long id, @RequestBody Lote lote) {
        return service.atualizar(id, lote)
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
