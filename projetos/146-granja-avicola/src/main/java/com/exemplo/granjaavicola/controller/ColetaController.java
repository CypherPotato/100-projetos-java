package com.exemplo.granjaavicola.controller;

import com.exemplo.granjaavicola.model.Coleta;
import com.exemplo.granjaavicola.service.ColetaService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/coletas")
public class ColetaController {

    private final ColetaService service;

    public ColetaController(ColetaService service) {
        this.service = service;
    }

    @GetMapping
    public List<Coleta> listar(@RequestParam(required = false) Long loteId) {
        return service.listar(loteId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Coleta> buscar(@PathVariable Long id) {
        return service.buscar(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Coleta criar(@RequestBody Coleta coleta) {
        return service.criar(coleta);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Coleta> atualizar(@PathVariable Long id, @RequestBody Coleta coleta) {
        return service.atualizar(id, coleta)
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
