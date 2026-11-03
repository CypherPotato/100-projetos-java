package com.exemplo.graficarapida.controller;

import com.exemplo.graficarapida.model.Impressao;
import com.exemplo.graficarapida.repository.ImpressaoRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/impressoes")
public class ImpressaoController {

    private final ImpressaoRepository repository;

    public ImpressaoController(ImpressaoRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Impressao> listar(@RequestParam(required = false) Long clienteId) {
        if (clienteId != null) {
            return repository.findByClienteId(clienteId);
        }
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Impressao> buscar(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Impressao criar(@RequestBody Impressao impressao) {
        return repository.save(impressao);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Impressao> atualizar(@PathVariable Long id, @RequestBody Impressao impressao) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        impressao.setId(id);
        return ResponseEntity.ok(repository.save(impressao));
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
