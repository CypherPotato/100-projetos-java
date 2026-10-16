package com.exemplo.adega.controller;

import com.exemplo.adega.model.Vinicola;
import com.exemplo.adega.service.VinicolaService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/vinicolas")
public class VinicolaController {

    private final VinicolaService service;

    public VinicolaController(VinicolaService service) {
        this.service = service;
    }

    @GetMapping
    public List<Vinicola> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Vinicola> buscar(@PathVariable Long id) {
        return service.buscar(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Vinicola criar(@RequestBody Vinicola vinicola) {
        return service.criar(vinicola);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Vinicola> atualizar(@PathVariable Long id, @RequestBody Vinicola vinicola) {
        return service.atualizar(id, vinicola)
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
