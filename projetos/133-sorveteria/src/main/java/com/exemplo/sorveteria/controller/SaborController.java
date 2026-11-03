package com.exemplo.sorveteria.controller;

import com.exemplo.sorveteria.model.Sabor;
import com.exemplo.sorveteria.repository.SaborRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sabores")
public class SaborController {

    private final SaborRepository repository;

    public SaborController(SaborRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Sabor> listar() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Sabor> buscar(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Sabor criar(@RequestBody Sabor sabor) {
        return repository.save(sabor);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Sabor> atualizar(@PathVariable Long id, @RequestBody Sabor sabor) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        sabor.setId(id);
        return ResponseEntity.ok(repository.save(sabor));
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
