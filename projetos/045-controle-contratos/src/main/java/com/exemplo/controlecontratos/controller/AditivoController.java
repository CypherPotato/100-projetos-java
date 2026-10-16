package com.exemplo.controlecontratos.controller;

import com.exemplo.controlecontratos.model.Aditivo;
import com.exemplo.controlecontratos.service.AditivoService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/aditivos")
public class AditivoController {

    private final AditivoService service;

    public AditivoController(AditivoService service) {
        this.service = service;
    }

    @GetMapping
    public List<Aditivo> listar(@RequestParam(required = false) Long contratoId) {
        return service.listar(contratoId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Aditivo> buscar(@PathVariable Long id) {
        return service.buscar(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Aditivo criar(@RequestBody Aditivo aditivo) {
        return service.criar(aditivo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Aditivo> atualizar(@PathVariable Long id, @RequestBody Aditivo aditivo) {
        return service.atualizar(id, aditivo)
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
