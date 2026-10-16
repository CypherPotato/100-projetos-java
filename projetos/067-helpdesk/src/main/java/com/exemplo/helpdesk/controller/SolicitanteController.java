package com.exemplo.helpdesk.controller;

import com.exemplo.helpdesk.model.Solicitante;
import com.exemplo.helpdesk.repository.SolicitanteRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/solicitantes")
public class SolicitanteController {

    private final SolicitanteRepository repository;

    public SolicitanteController(SolicitanteRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Solicitante> listar() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Solicitante> buscar(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Solicitante criar(@RequestBody Solicitante solicitante) {
        return repository.save(solicitante);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Solicitante> atualizar(@PathVariable Long id, @RequestBody Solicitante solicitante) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        solicitante.setId(id);
        return ResponseEntity.ok(repository.save(solicitante));
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
