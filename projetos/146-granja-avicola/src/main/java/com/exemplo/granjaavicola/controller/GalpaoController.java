package com.exemplo.granjaavicola.controller;

import com.exemplo.granjaavicola.model.Galpao;
import com.exemplo.granjaavicola.service.GalpaoService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/galpoes")
public class GalpaoController {

    private final GalpaoService service;

    public GalpaoController(GalpaoService service) {
        this.service = service;
    }

    @GetMapping
    public List<Galpao> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Galpao> buscar(@PathVariable Long id) {
        return service.buscar(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Galpao criar(@RequestBody Galpao galpao) {
        return service.criar(galpao);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Galpao> atualizar(@PathVariable Long id, @RequestBody Galpao galpao) {
        return service.atualizar(id, galpao)
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
