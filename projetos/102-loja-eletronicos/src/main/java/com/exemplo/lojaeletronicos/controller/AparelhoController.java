package com.exemplo.lojaeletronicos.controller;

import com.exemplo.lojaeletronicos.model.Aparelho;
import com.exemplo.lojaeletronicos.repository.AparelhoRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/aparelhos")
public class AparelhoController {

    private final AparelhoRepository repository;

    public AparelhoController(AparelhoRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Aparelho> listar(@RequestParam(required = false) Long marcaId) {
        if (marcaId != null) {
            return repository.findByMarcaId(marcaId);
        }
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Aparelho> buscar(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Aparelho criar(@RequestBody Aparelho aparelho) {
        return repository.save(aparelho);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Aparelho> atualizar(@PathVariable Long id, @RequestBody Aparelho aparelho) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        aparelho.setId(id);
        return ResponseEntity.ok(repository.save(aparelho));
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
