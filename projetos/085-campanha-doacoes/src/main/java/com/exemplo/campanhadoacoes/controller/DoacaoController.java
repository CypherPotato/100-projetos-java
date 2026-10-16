package com.exemplo.campanhadoacoes.controller;

import com.exemplo.campanhadoacoes.model.Doacao;
import com.exemplo.campanhadoacoes.repository.DoacaoRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/doacoes")
public class DoacaoController {

    private final DoacaoRepository repository;

    public DoacaoController(DoacaoRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Doacao> listar(@RequestParam(required = false) Long campanhaId, @RequestParam(required = false) Long doadorId) {
        if (campanhaId != null) {
            return repository.findByCampanhaId(campanhaId);
        }
        if (doadorId != null) {
            return repository.findByDoadorId(doadorId);
        }
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Doacao> buscar(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Doacao criar(@RequestBody Doacao doacao) {
        return repository.save(doacao);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Doacao> atualizar(@PathVariable Long id, @RequestBody Doacao doacao) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        doacao.setId(id);
        return ResponseEntity.ok(repository.save(doacao));
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
