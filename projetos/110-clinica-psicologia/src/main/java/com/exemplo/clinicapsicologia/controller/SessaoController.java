package com.exemplo.clinicapsicologia.controller;

import com.exemplo.clinicapsicologia.model.Sessao;
import com.exemplo.clinicapsicologia.service.SessaoService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sessoes")
public class SessaoController {

    private final SessaoService service;

    public SessaoController(SessaoService service) {
        this.service = service;
    }

    @GetMapping
    public List<Sessao> listar(@RequestParam(required = false) Long psicologoId, @RequestParam(required = false) Long pacienteId) {
        return service.listar(psicologoId, pacienteId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Sessao> buscar(@PathVariable Long id) {
        return service.buscar(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Sessao criar(@RequestBody Sessao sessao) {
        return service.criar(sessao);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Sessao> atualizar(@PathVariable Long id, @RequestBody Sessao sessao) {
        return service.atualizar(id, sessao)
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
