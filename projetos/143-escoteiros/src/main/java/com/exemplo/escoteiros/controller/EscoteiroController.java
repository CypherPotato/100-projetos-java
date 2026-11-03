package com.exemplo.escoteiros.controller;

import com.exemplo.escoteiros.model.Escoteiro;
import com.exemplo.escoteiros.service.EscoteiroService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/escoteiros")
public class EscoteiroController {

    private final EscoteiroService service;

    public EscoteiroController(EscoteiroService service) {
        this.service = service;
    }

    @GetMapping
    public List<Escoteiro> listar(@RequestParam(required = false) Long patrulhaId) {
        return service.listar(patrulhaId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Escoteiro> buscar(@PathVariable Long id) {
        return service.buscar(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Escoteiro criar(@RequestBody Escoteiro escoteiro) {
        return service.criar(escoteiro);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Escoteiro> atualizar(@PathVariable Long id, @RequestBody Escoteiro escoteiro) {
        return service.atualizar(id, escoteiro)
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
