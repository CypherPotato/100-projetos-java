package com.exemplo.creche.controller;

import com.exemplo.creche.model.Crianca;
import com.exemplo.creche.service.CriancaService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/criancas")
public class CriancaController {

    private final CriancaService service;

    public CriancaController(CriancaService service) {
        this.service = service;
    }

    @GetMapping
    public List<Crianca> listar(@RequestParam(required = false) Long responsavelId) {
        return service.listar(responsavelId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Crianca> buscar(@PathVariable Long id) {
        return service.buscar(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Crianca criar(@RequestBody Crianca crianca) {
        return service.criar(crianca);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Crianca> atualizar(@PathVariable Long id, @RequestBody Crianca crianca) {
        return service.atualizar(id, crianca)
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
