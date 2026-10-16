package com.exemplo.hospital.controller;

import com.exemplo.hospital.model.Internacao;
import com.exemplo.hospital.repository.InternacaoRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/internacoes")
public class InternacaoController {

    private final InternacaoRepository repository;

    public InternacaoController(InternacaoRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Internacao> listar(@RequestParam(required = false) Long leitoId) {
        if (leitoId != null) {
            return repository.findByLeitoId(leitoId);
        }
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Internacao> buscar(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Internacao criar(@RequestBody Internacao internacao) {
        return repository.save(internacao);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Internacao> atualizar(@PathVariable Long id, @RequestBody Internacao internacao) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        internacao.setId(id);
        return ResponseEntity.ok(repository.save(internacao));
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
