package com.exemplo.protocolodocumentos.controller;

import com.exemplo.protocolodocumentos.model.Tramitacao;
import com.exemplo.protocolodocumentos.service.TramitacaoService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tramitacoes")
public class TramitacaoController {

    private final TramitacaoService service;

    public TramitacaoController(TramitacaoService service) {
        this.service = service;
    }

    @GetMapping
    public List<Tramitacao> listar(@RequestParam(required = false) Long documentoId) {
        return service.listar(documentoId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tramitacao> buscar(@PathVariable Long id) {
        return service.buscar(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Tramitacao criar(@RequestBody Tramitacao tramitacao) {
        return service.criar(tramitacao);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tramitacao> atualizar(@PathVariable Long id, @RequestBody Tramitacao tramitacao) {
        return service.atualizar(id, tramitacao)
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
