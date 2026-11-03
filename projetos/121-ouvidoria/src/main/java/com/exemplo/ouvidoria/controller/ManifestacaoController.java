package com.exemplo.ouvidoria.controller;

import com.exemplo.ouvidoria.model.Manifestacao;
import com.exemplo.ouvidoria.repository.ManifestacaoRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/manifestacoes")
public class ManifestacaoController {

    private final ManifestacaoRepository repository;

    public ManifestacaoController(ManifestacaoRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Manifestacao> listar(@RequestParam(required = false) Long departamentoId) {
        if (departamentoId != null) {
            return repository.findByDepartamentoId(departamentoId);
        }
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Manifestacao> buscar(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Manifestacao criar(@RequestBody Manifestacao manifestacao) {
        return repository.save(manifestacao);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Manifestacao> atualizar(@PathVariable Long id, @RequestBody Manifestacao manifestacao) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        manifestacao.setId(id);
        return ResponseEntity.ok(repository.save(manifestacao));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
