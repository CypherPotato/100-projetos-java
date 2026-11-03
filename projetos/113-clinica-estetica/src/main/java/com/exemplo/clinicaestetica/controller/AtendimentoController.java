package com.exemplo.clinicaestetica.controller;

import com.exemplo.clinicaestetica.model.Atendimento;
import com.exemplo.clinicaestetica.service.AtendimentoService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/atendimentos")
public class AtendimentoController {

    private final AtendimentoService service;

    public AtendimentoController(AtendimentoService service) {
        this.service = service;
    }

    @GetMapping
    public List<Atendimento> listar(@RequestParam(required = false) Long procedimentoId) {
        return service.listar(procedimentoId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Atendimento> buscar(@PathVariable Long id) {
        return service.buscar(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Atendimento criar(@RequestBody Atendimento atendimento) {
        return service.criar(atendimento);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Atendimento> atualizar(@PathVariable Long id, @RequestBody Atendimento atendimento) {
        return service.atualizar(id, atendimento)
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
