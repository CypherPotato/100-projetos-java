package com.exemplo.escolaidiomas.controller;

import com.exemplo.escolaidiomas.model.Turma;
import com.exemplo.escolaidiomas.repository.TurmaRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/turmas")
public class TurmaController {

    private final TurmaRepository repository;

    public TurmaController(TurmaRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Turma> listar(@RequestParam(required = false) Long idiomaId, @RequestParam(required = false) Long professorId) {
        if (idiomaId != null) {
            return repository.findByIdiomaId(idiomaId);
        }
        if (professorId != null) {
            return repository.findByProfessorId(professorId);
        }
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Turma> buscar(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Turma criar(@RequestBody Turma turma) {
        return repository.save(turma);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Turma> atualizar(@PathVariable Long id, @RequestBody Turma turma) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        turma.setId(id);
        return ResponseEntity.ok(repository.save(turma));
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
