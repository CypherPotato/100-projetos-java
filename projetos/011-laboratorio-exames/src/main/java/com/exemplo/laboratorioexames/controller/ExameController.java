package com.exemplo.laboratorioexames.controller;

import com.exemplo.laboratorioexames.model.Exame;
import com.exemplo.laboratorioexames.repository.ExameRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/exames")
public class ExameController {

    private final ExameRepository repository;

    public ExameController(ExameRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Exame> listar(@RequestParam(required = false) Long tipoExameId, @RequestParam(required = false) Long pacienteId) {
        if (tipoExameId != null) {
            return repository.findByTipoExameId(tipoExameId);
        }
        if (pacienteId != null) {
            return repository.findByPacienteId(pacienteId);
        }
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Exame> buscar(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Exame criar(@RequestBody Exame exame) {
        return repository.save(exame);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Exame> atualizar(@PathVariable Long id, @RequestBody Exame exame) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        exame.setId(id);
        return ResponseEntity.ok(repository.save(exame));
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
