package com.exemplo.protocolodocumentos.controller;

import com.exemplo.protocolodocumentos.model.Documento;
import com.exemplo.protocolodocumentos.service.DocumentoService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/documentos")
public class DocumentoController {

    private final DocumentoService service;

    public DocumentoController(DocumentoService service) {
        this.service = service;
    }

    @GetMapping
    public List<Documento> listar(@RequestParam(required = false) Long setorId) {
        return service.listar(setorId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Documento> buscar(@PathVariable Long id) {
        return service.buscar(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Documento criar(@RequestBody Documento documento) {
        return service.criar(documento);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Documento> atualizar(@PathVariable Long id, @RequestBody Documento documento) {
        return service.atualizar(id, documento)
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
