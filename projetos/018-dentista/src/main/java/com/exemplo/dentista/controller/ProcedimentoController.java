package com.exemplo.dentista.controller;

import com.exemplo.dentista.model.Procedimento;
import com.exemplo.dentista.repository.ProcedimentoRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/procedimentos")
public class ProcedimentoController {

    private final ProcedimentoRepository repository;

    public ProcedimentoController(ProcedimentoRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Procedimento> listar(@RequestParam(required = false) Long dentistaId) {
        if (dentistaId != null) {
            return repository.findByDentistaId(dentistaId);
        }
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Procedimento> buscar(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Procedimento criar(@RequestBody Procedimento procedimento) {
        return repository.save(procedimento);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Procedimento> atualizar(@PathVariable Long id, @RequestBody Procedimento procedimento) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        procedimento.setId(id);
        return ResponseEntity.ok(repository.save(procedimento));
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
