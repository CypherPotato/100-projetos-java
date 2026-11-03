package com.exemplo.featureflags.controller;

import com.exemplo.featureflags.model.Flag;
import com.exemplo.featureflags.repository.FlagRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/flags")
public class FlagController {

    private final FlagRepository repository;

    public FlagController(FlagRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Flag> listar(@RequestParam(required = false) Long aplicacaoId) {
        if (aplicacaoId != null) {
            return repository.findByAplicacaoId(aplicacaoId);
        }
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Flag> buscar(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Flag criar(@RequestBody Flag flag) {
        return repository.save(flag);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Flag> atualizar(@PathVariable Long id, @RequestBody Flag flag) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        flag.setId(id);
        return ResponseEntity.ok(repository.save(flag));
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
