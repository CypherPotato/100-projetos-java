package com.exemplo.clubesocial.controller;

import com.exemplo.clubesocial.model.Socio;
import com.exemplo.clubesocial.service.SocioService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/socios")
public class SocioController {

    private final SocioService service;

    public SocioController(SocioService service) {
        this.service = service;
    }

    @GetMapping
    public List<Socio> listar(@RequestParam(required = false) Long modalidadeId) {
        return service.listar(modalidadeId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Socio> buscar(@PathVariable Long id) {
        return service.buscar(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Socio criar(@RequestBody Socio socio) {
        return service.criar(socio);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Socio> atualizar(@PathVariable Long id, @RequestBody Socio socio) {
        return service.atualizar(id, socio)
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
