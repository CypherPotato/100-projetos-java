package com.exemplo.fazenda.controller;

import com.exemplo.fazenda.model.Talhao;
import com.exemplo.fazenda.repository.TalhaoRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/talhoes")
public class TalhaoController {

    private final TalhaoRepository repository;

    public TalhaoController(TalhaoRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Talhao> listar() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Talhao> buscar(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Talhao criar(@RequestBody Talhao talhao) {
        return repository.save(talhao);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Talhao> atualizar(@PathVariable Long id, @RequestBody Talhao talhao) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        talhao.setId(id);
        return ResponseEntity.ok(repository.save(talhao));
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
