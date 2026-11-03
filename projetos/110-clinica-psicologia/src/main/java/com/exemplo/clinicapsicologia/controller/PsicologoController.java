package com.exemplo.clinicapsicologia.controller;

import com.exemplo.clinicapsicologia.model.Psicologo;
import com.exemplo.clinicapsicologia.service.PsicologoService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/psicologos")
public class PsicologoController {

    private final PsicologoService service;

    public PsicologoController(PsicologoService service) {
        this.service = service;
    }

    @GetMapping
    public List<Psicologo> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Psicologo> buscar(@PathVariable Long id) {
        return service.buscar(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Psicologo criar(@RequestBody Psicologo psicologo) {
        return service.criar(psicologo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Psicologo> atualizar(@PathVariable Long id, @RequestBody Psicologo psicologo) {
        return service.atualizar(id, psicologo)
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
