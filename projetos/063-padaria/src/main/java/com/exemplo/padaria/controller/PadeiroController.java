package com.exemplo.padaria.controller;

import com.exemplo.padaria.model.Padeiro;
import com.exemplo.padaria.service.PadeiroService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/padeiros")
public class PadeiroController {

    private final PadeiroService service;

    public PadeiroController(PadeiroService service) {
        this.service = service;
    }

    @GetMapping
    public List<Padeiro> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Padeiro> buscar(@PathVariable Long id) {
        return service.buscar(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Padeiro criar(@RequestBody Padeiro padeiro) {
        return service.criar(padeiro);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Padeiro> atualizar(@PathVariable Long id, @RequestBody Padeiro padeiro) {
        return service.atualizar(id, padeiro)
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
