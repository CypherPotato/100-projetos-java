package com.exemplo.assistenciatecnica.controller;

import com.exemplo.assistenciatecnica.model.Aparelho;
import com.exemplo.assistenciatecnica.service.AparelhoService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/aparelhos")
public class AparelhoController {

    private final AparelhoService service;

    public AparelhoController(AparelhoService service) {
        this.service = service;
    }

    @GetMapping
    public List<Aparelho> listar(@RequestParam(required = false) Long clienteId) {
        return service.listar(clienteId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Aparelho> buscar(@PathVariable Long id) {
        return service.buscar(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Aparelho criar(@RequestBody Aparelho aparelho) {
        return service.criar(aparelho);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Aparelho> atualizar(@PathVariable Long id, @RequestBody Aparelho aparelho) {
        return service.atualizar(id, aparelho)
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
