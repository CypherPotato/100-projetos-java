package com.exemplo.marmitaria.controller;

import com.exemplo.marmitaria.model.Encomenda;
import com.exemplo.marmitaria.repository.EncomendaRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/encomendas")
public class EncomendaController {

    private final EncomendaRepository repository;

    public EncomendaController(EncomendaRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Encomenda> listar(@RequestParam(required = false) Long marmitaId) {
        if (marmitaId != null) {
            return repository.findByMarmitaId(marmitaId);
        }
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Encomenda> buscar(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Encomenda criar(@RequestBody Encomenda encomenda) {
        return repository.save(encomenda);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Encomenda> atualizar(@PathVariable Long id, @RequestBody Encomenda encomenda) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        encomenda.setId(id);
        return ResponseEntity.ok(repository.save(encomenda));
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
