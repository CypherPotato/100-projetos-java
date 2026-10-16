package com.exemplo.clubeleitura.controller;

import com.exemplo.clubeleitura.model.Leitura;
import com.exemplo.clubeleitura.repository.LeituraRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/leituras")
public class LeituraController {

    private final LeituraRepository repository;

    public LeituraController(LeituraRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Leitura> listar(@RequestParam(required = false) Long livroId, @RequestParam(required = false) Long membroId) {
        if (livroId != null) {
            return repository.findByLivroId(livroId);
        }
        if (membroId != null) {
            return repository.findByMembroId(membroId);
        }
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Leitura> buscar(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Leitura criar(@RequestBody Leitura leitura) {
        return repository.save(leitura);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Leitura> atualizar(@PathVariable Long id, @RequestBody Leitura leitura) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        leitura.setId(id);
        return ResponseEntity.ok(repository.save(leitura));
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
