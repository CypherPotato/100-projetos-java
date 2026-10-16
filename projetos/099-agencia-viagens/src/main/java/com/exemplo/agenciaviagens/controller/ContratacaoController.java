package com.exemplo.agenciaviagens.controller;

import com.exemplo.agenciaviagens.model.Contratacao;
import com.exemplo.agenciaviagens.service.ContratacaoService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/contratacoes")
public class ContratacaoController {

    private final ContratacaoService service;

    public ContratacaoController(ContratacaoService service) {
        this.service = service;
    }

    @GetMapping
    public List<Contratacao> listar(@RequestParam(required = false) Long pacoteId, @RequestParam(required = false) Long clienteId) {
        return service.listar(pacoteId, clienteId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Contratacao> buscar(@PathVariable Long id) {
        return service.buscar(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Contratacao criar(@RequestBody Contratacao contratacao) {
        return service.criar(contratacao);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Contratacao> atualizar(@PathVariable Long id, @RequestBody Contratacao contratacao) {
        return service.atualizar(id, contratacao)
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
