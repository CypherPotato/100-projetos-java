package com.exemplo.helpdesk.controller;

import com.exemplo.helpdesk.model.Chamado;
import com.exemplo.helpdesk.repository.ChamadoRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/chamados")
public class ChamadoController {

    private final ChamadoRepository repository;

    public ChamadoController(ChamadoRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Chamado> listar(@RequestParam(required = false) Long solicitanteId) {
        if (solicitanteId != null) {
            return repository.findBySolicitanteId(solicitanteId);
        }
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Chamado> buscar(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Chamado criar(@RequestBody Chamado chamado) {
        return repository.save(chamado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Chamado> atualizar(@PathVariable Long id, @RequestBody Chamado chamado) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        chamado.setId(id);
        return ResponseEntity.ok(repository.save(chamado));
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
