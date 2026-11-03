package com.exemplo.gestaoobras.controller;

import com.exemplo.gestaoobras.model.Etapa;
import com.exemplo.gestaoobras.service.EtapaService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/etapas")
public class EtapaController {

    private final EtapaService service;

    public EtapaController(EtapaService service) {
        this.service = service;
    }

    @GetMapping
    public List<Etapa> listar(@RequestParam(required = false) Long obraId) {
        return service.listar(obraId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Etapa> buscar(@PathVariable Long id) {
        return service.buscar(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Etapa criar(@RequestBody Etapa etapa) {
        return service.criar(etapa);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Etapa> atualizar(@PathVariable Long id, @RequestBody Etapa etapa) {
        return service.atualizar(id, etapa)
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
