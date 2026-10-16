package com.exemplo.encanador.controller;

import com.exemplo.encanador.model.VisitaTecnica;
import com.exemplo.encanador.service.VisitaTecnicaService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/visitas-tecnicas")
public class VisitaTecnicaController {

    private final VisitaTecnicaService service;

    public VisitaTecnicaController(VisitaTecnicaService service) {
        this.service = service;
    }

    @GetMapping
    public List<VisitaTecnica> listar(@RequestParam(required = false) Long clienteId) {
        return service.listar(clienteId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VisitaTecnica> buscar(@PathVariable Long id) {
        return service.buscar(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VisitaTecnica criar(@RequestBody VisitaTecnica visitaTecnica) {
        return service.criar(visitaTecnica);
    }

    @PutMapping("/{id}")
    public ResponseEntity<VisitaTecnica> atualizar(@PathVariable Long id, @RequestBody VisitaTecnica visitaTecnica) {
        return service.atualizar(id, visitaTecnica)
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
