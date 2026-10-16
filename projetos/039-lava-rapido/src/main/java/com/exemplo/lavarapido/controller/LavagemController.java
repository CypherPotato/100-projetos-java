package com.exemplo.lavarapido.controller;

import com.exemplo.lavarapido.model.Lavagem;
import com.exemplo.lavarapido.service.LavagemService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/lavagens")
public class LavagemController {

    private final LavagemService service;

    public LavagemController(LavagemService service) {
        this.service = service;
    }

    @GetMapping
    public List<Lavagem> listar(@RequestParam(required = false) Long veiculoId) {
        return service.listar(veiculoId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Lavagem> buscar(@PathVariable Long id) {
        return service.buscar(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Lavagem criar(@RequestBody Lavagem lavagem) {
        return service.criar(lavagem);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Lavagem> atualizar(@PathVariable Long id, @RequestBody Lavagem lavagem) {
        return service.atualizar(id, lavagem)
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
