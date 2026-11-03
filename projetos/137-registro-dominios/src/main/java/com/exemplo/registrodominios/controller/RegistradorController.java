package com.exemplo.registrodominios.controller;

import com.exemplo.registrodominios.model.Registrador;
import com.exemplo.registrodominios.service.RegistradorService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/registradores")
public class RegistradorController {

    private final RegistradorService service;

    public RegistradorController(RegistradorService service) {
        this.service = service;
    }

    @GetMapping
    public List<Registrador> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Registrador> buscar(@PathVariable Long id) {
        return service.buscar(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Registrador criar(@RequestBody Registrador registrador) {
        return service.criar(registrador);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Registrador> atualizar(@PathVariable Long id, @RequestBody Registrador registrador) {
        return service.atualizar(id, registrador)
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
