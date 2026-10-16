package com.exemplo.coopagricola.controller;

import com.exemplo.coopagricola.model.Recebimento;
import com.exemplo.coopagricola.repository.RecebimentoRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/recebimentos")
public class RecebimentoController {

    private final RecebimentoRepository repository;

    public RecebimentoController(RecebimentoRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Recebimento> listar(@RequestParam(required = false) Long agricultorId) {
        if (agricultorId != null) {
            return repository.findByAgricultorId(agricultorId);
        }
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Recebimento> buscar(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Recebimento criar(@RequestBody Recebimento recebimento) {
        return repository.save(recebimento);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Recebimento> atualizar(@PathVariable Long id, @RequestBody Recebimento recebimento) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        recebimento.setId(id);
        return ResponseEntity.ok(repository.save(recebimento));
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
